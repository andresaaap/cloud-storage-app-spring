package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    // create fileid variable
    private int fileId;
    // create filename variable
    private String fileName;
    // create contenttype variable
    private String contentType;
    // create filesize variable
    private String fileSize;
    // create userid variable
    private int userId;
    // create filedata variable
    private byte[] fileData;

    // create constructor
    public File(int fileId, String fileName, String contentType, String fileSize, int userId, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

    // getter and setter for fileid
    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    // getter and setter for filename
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // getter and setter for contenttype
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // getter and setter for filesize
    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    // getter and setter for userid
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // getter and setter for filedata
    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

}
