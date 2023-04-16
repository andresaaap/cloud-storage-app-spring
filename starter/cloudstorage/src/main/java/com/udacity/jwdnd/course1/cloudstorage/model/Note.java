package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    // create noteid variable
    private int noteid;
    // create notetitle variable
    private String notetitle;
    // create notedescription variable
    private String notedescription;
    // create userid variable
    private int userid;

    // create constructor
    public Note(int noteid, String notetitle, String notedescription, int userid) {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
    }

    // getter and setter for noteid
    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    // getter and setter for notetitle
    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    // getter and setter for notedescription
    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    // getter and setter for userid
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
