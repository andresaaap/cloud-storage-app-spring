package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    // get all credentials in mybatis
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentials(Integer userid);
    // create a method called getCredential in mybatis
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(int credentialid);
    // get all credentials by userid in mybatis
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentialsByUserId(int userid);

    // get a credential by username and userid in mybatis
    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username} AND userid = #{userid}")
    Credential getCredentialByUsername(String username, int userid);
    // insert a credential with a url, username, key, password, and userid in mybatis
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    int insertCredential(Credential credential);
    // update a credential with a url, username, key, password, and userid in mybatis
    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialid}")
    int updateCredential(String url, String username, String password, int credentialid);
    // delete a credential by credentialid in mybatis
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int deleteCredential(int credentialid);
}
