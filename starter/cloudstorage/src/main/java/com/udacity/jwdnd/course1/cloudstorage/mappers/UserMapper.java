package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // select a user by username called getUser
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    //insert a user with a username, passwordHash, firstname, and lastname
    @Insert("INSERT INTO USERS (username, salt, passwordHash, firstname, lastname) VALUES (#{username}, #{salt}, #{passwordHash}, #{firstname}, #{lastname})")
    int createUser(String username, String salt, String passwordHash, String firstname, String lastname);
}
