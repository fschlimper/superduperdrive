package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UploadFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UploadFileMapper {

    @Insert("INSERT INTO FILES(fileid, filename, contenttype, filesize, userid, filedata) VALUES (#{fileid}, #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(keyProperty = "fileid", useGeneratedKeys = true)
    public int uploadFile(UploadFile uploadFile);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    public List<UploadFile> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    public UploadFile getFileByFileId(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} and userid  =#{userId}")
    public UploadFile getFileByUserIdAndFilename(Integer userId, String filename);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    public int deleteFile(Integer fileId);
}
