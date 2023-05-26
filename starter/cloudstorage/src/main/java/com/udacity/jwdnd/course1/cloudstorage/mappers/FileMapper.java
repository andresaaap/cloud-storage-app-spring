package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    // get all files called getFiles using mybatis
    @Select("SELECT * FROM FILES")
    List<File> getFiles();
    // select a file by fileId called getFile
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);
    // select all files by userId called getFilesByUserId
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFilesByUserId(Integer userId);
    // select a file by filename and userid called getFileByFilename
    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    File getFileByFilename(String fileName, Integer userId);
    // insert a file with a filename, contenttype, filesize, userid, and filedata
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    Integer insertFile(File file);
    // Delete a file by fileId called deleteFile using mybatis
    @Select("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer deleteFile(Integer fileId);




}
