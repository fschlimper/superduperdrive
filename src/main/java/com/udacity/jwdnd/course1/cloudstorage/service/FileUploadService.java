package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UploadFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UploadFile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileUploadService {

    private UploadFileMapper uploadFileMapper;

    public FileUploadService(UploadFileMapper uploadFileMapper) {
        this.uploadFileMapper = uploadFileMapper;
    }

    /**
     * Uploads a file.
     *
     * @param uploadFile The file to upload
     * @return An error message, null if the operation has been successful
     */
    public String uploadFile(UploadFile uploadFile, Integer userId) {
        String result = null;

        if (uploadFile == null || uploadFile.getFilename() == null || uploadFile.getFilename().isEmpty()) {
            return "Filename is empty.";
        }

        UploadFile fileInDb = uploadFileMapper.getFileByUserIdAndFilename(userId, uploadFile.getFilename());
        if (fileInDb != null) {
            return "File with filename " + uploadFile.getFilename() + " exists.";
        }

        int uploadResult = uploadFileMapper.uploadFile(uploadFile);


        return uploadResult == 1 ? null : "An error occurred while uploading.";

    }

    /**
     * Deletes the file with the given ID.
     *
     * @param fileId The file's ID
     * @return An error message, null if the operation has been successful
     */
    public String deleteFile(Integer fileId) {
        return uploadFileMapper.deleteFile(fileId) == 1 ? null : "An error occurred while deleting.";
    }

    public List<UploadFile> getFilesByUserId(Integer userId) {
        return uploadFileMapper.getFilesByUserId(userId);
    }

    public UploadFile getFileByFileId(Integer fileId) {
        return uploadFileMapper.getFileByFileId(fileId);
    }

}
