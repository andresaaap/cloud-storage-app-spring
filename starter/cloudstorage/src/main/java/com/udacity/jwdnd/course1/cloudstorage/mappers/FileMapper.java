package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {
    // select a file by fileId called getFile
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(int fileId);
    // insert a file with a filename, contenttype, filesize, userid, and filedata
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    int insertFile(String filename, String contenttype, String filesize, int userid, byte[] filedata);
    // Delete a file by fileId called deleteFile using mybatis
    @Select("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(int fileId);




}
