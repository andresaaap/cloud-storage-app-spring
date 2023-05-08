package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    // get all credentials in mybatis
    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getCredentials();
    // create a method called getCredential in mybatis
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(int credentialid);
    // insert a credential with a url, username, key, password, and userid in mybatis
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    int insertCredential(Credential credential);
    // update a credential with a url, username, key, password, and userid in mybatis
    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userid} WHERE credentialid = #{credentialid}")
    int updateCredential(String url, String username, String key, String password, int userid, int credentialid);
    // delete a credential by credentialid in mybatis
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int deleteCredential(int credentialid);
}
