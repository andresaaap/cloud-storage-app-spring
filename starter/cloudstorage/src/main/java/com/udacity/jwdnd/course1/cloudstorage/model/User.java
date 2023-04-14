package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {
    private int userid;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;

    public User(int userid, String username, String salt, String password, String firstname, String lastname) {
        this.userid = userid;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // getter and setter for userid
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    // getter and setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // getter and setter for salt
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    // getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // getter and setter for firstname
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    // getter and setter for lastname
    public String getLastname() {
        return lastname;
    }
    //setter for lastname
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
