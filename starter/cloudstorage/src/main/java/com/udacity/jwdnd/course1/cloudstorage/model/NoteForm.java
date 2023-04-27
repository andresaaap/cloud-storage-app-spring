package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private String notetitle;
    // create notedescription variable
    private String notedescription;
    // create userid variable
    private Integer userid;

    // create constructor
    public NoteForm(String notetitle, String notedescription, Integer userid) {
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
