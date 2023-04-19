package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    // select note by noteid using mybatis
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNoteByNoteid(int noteid);
    // insert note using mybatis
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    int insertNote(String notetitle, String notedescription, int userid);
    // update note using mybatis
    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    int updateNote(String notetitle, String notedescription, int noteid);
    // delete note using mybatis
    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int deleteNote(int noteid);

}
