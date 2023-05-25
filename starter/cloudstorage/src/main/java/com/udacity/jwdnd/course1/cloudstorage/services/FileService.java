package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    // create file mapper variable
    private FileMapper fileMapper;
    // create constructor
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    // create a method to add a file
    public Integer addFile(File file) {
        return fileMapper.insertFile(file);
    }

    // create a method to get all files
    public List<File> getFiles() {
        return fileMapper.getFiles();
    }

    // create a method to get a file by fileId
    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    // create a method to get a file by userId
    public List<File> getFilesByUserId(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    // delete file by fileId
    public Integer deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }
}
