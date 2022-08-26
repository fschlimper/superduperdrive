package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CleanDbMapper {

    @Delete("DELETE FROM USERS")
    public void cleanAllUsers();

    @Delete("DELETE FROM FILES")
    public void cleanAllFiles();

    @Delete("DELETE FROM NOTES")
    public void cleanAllNotes();

    @Delete("DELETE FROM CREDENTIALS")
    public void cleanAllCredentials();
}
