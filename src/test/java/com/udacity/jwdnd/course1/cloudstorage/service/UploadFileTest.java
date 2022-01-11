package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UploadFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UploadFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UploadFileTest {

    private final int USERID = 1;
    private final String FILENAME = "test.txt";

    @Mock
    private UploadFileMapper uploadFileMapper;

    @Mock
    private UploadFile uploadFile;

    @InjectMocks
    private FileUploadService fileUploadService;

    @Test
    public void testSuccessfulFileUpload() {
        when(uploadFile.getFilename()).thenReturn(FILENAME);
        when(uploadFileMapper.getFileByUserIdAndFilename(USERID, FILENAME)).thenReturn(null);
        when(uploadFileMapper.uploadFile(uploadFile)).thenReturn(1);

        String uploadResult = fileUploadService.uploadFile(uploadFile, USERID);

        assertNull(uploadResult);
    }

    @Test
    public void testFileExists() {
        when(uploadFile.getFilename()).thenReturn(FILENAME);
        when(uploadFileMapper.getFileByUserIdAndFilename(USERID, FILENAME)).thenReturn(uploadFile);

        String uploadResult = fileUploadService.uploadFile(uploadFile, USERID);

        assertEquals("File with filename " + FILENAME + " exists.", uploadResult);
    }

    @Test
    public void testEmptyFilename() {
        when(uploadFile.getFilename()).thenReturn(null);

        String uploadResult = fileUploadService.uploadFile(uploadFile, USERID);

        assertEquals("Filename is empty.", uploadResult);

        when(uploadFile.getFilename()).thenReturn("");

        uploadResult = fileUploadService.uploadFile(uploadFile, USERID);

        assertEquals("Filename is empty.", uploadResult);
    }
}
