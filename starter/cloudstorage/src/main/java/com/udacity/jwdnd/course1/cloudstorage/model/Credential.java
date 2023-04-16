package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    // create credentialid variable using cammel case
    private int credentialId;
    // create url variable using cammel case
    private String url;
    // create username variable using cammel case
    private String username;
    // create key variable using cammel case
    private String key;
    // create password variable using cammel case
    private String password;
    // create userId variable using cammel case
    private int userId;

    // create constructor
    public Credential(int credentialId, String url, String username, String key, String password, int userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    // create setter and getter for credentialId
    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }

    // create setter and getter for url
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // create setter and getter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // create setter and getter for key
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    // create setter and getter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // create setter and getter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
