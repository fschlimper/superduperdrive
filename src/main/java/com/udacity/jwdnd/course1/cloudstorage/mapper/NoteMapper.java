package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES(noteid, title, note, userid) VALUES (#{noteid}, #{title}, #{note}, #{userid})")
    @Options(keyProperty = "noteid", useGeneratedKeys = true)
    Integer addNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    Integer deleteNote(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotesByUserId(Integer userId);

    @Select("SELECT * FROM NOTES WHERE title = #{title} AND userid = #{userid}")
    Note getNoteByTitle(String title, Integer userid);

    @Update("UPDATE NOTES SET title = #{title}, note = #{note} WHERE noteid = #{noteid}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES")
    void deleteAllNotes();
}
