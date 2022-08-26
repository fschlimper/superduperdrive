package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credentials> getCredentialsByUserId(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} AND url = #{url}")
    Credentials getCredentialsByUrl(Integer userId, String url);

    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid) VALUES (#{credentialid}, #{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(keyProperty = "credentialid", useGeneratedKeys = true)
    Integer insertCredentials(Credentials credentials);

    @Delete("DELETE CREDENTIALS WHERE credentialId = #{credentialId}")
    Integer deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialid}")
    Integer updateCredentials(Credentials credentials);
}
