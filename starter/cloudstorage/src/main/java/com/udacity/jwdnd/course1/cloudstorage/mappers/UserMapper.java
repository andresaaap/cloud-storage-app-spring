package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // select a user by username called getUser
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    // select a user by userid called getUserById
    @Select("SELECT * FROM USERS WHERE userid = #{userid}")
    User getUserById(Integer userid);

    // select all users called getAllUsers
    @Select("SELECT * FROM USERS")
    List<User> getAllUsers();

    //insert a user with a username, passwordHash, firstname, and lastname
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(User user);

    //delete a user by userid
    @Delete("DELETE FROM USERS WHERE userid = #{userid}")
    void deleteUser(Integer userid);
}
