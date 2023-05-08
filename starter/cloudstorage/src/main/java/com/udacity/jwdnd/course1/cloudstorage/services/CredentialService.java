package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    // get all credentials
    public List<Credential> getCredentials() {

        List<Credential> credentials = credentialMapper.getCredentials();

        // loop through the credentials and decrypt the password
        for (Credential credential : credentials) {
            String encryptedPassword = credential.getPassword();
            String encodedKey = credential.getKey();
            String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
            credential.setPassword(decryptedPassword);
        }

        return credentials;
    }

    // add new credential and has a parameter of type CredentialForm
    public int addCredential(CredentialForm credentialForm) {

            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            // encrypt the password
            String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
            // create a new credential object
            Credential credential = new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(), encodedKey, encryptedPassword, credentialForm.getUserId());
            return credentialMapper.insertCredential(credential);

    }

    // delete credential by credentialid
    public int deleteCredential(int credentialid) {
        return credentialMapper.deleteCredential(credentialid);
    }

    // update credential by credentialid
    public int updateCredential(CredentialForm credentialForm) {
        Credential credential = credentialMapper.getCredential(credentialForm.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), credential.getKey());
        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(encryptedPassword);
        return credentialMapper.updateCredential(credential.getUrl(), credential.getUsername(), credential.getPassword(), credentialForm.getCredentialId());
    }


}
