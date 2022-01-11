package com.udacity.jwdnd.course1.cloudstorage.selenium;

import com.udacity.jwdnd.course1.cloudstorage.pages.FileUploadPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadTest extends CloudStorageBaseTest {

    @Test
    public void testFileUpload() {
        signupUser(TESTDATA_FIRSTNAME, TESTDATA_LASTNAME, TESTDATA_USERNAME, TESTDATA_PASSWORD);
        loginUser(TESTDATA_USERNAME, TESTDATA_PASSWORD);
        FileUploadPage fileUploadPage = uploadFile(TESTDATA_LOCAL_FILEPATH);
        assertTrue(fileUploadPage.existsFileEntry(TESTDATA_FILENAME));
        assertEquals("Upload successful!", fileUploadPage.getInfoMessage());
        assertEquals(1, fileUploadPage.getFileTableSize());

        uploadFile(TESTDATA_LOCAL_FILEPATH2);
        assertTrue(fileUploadPage.existsFileEntry(TESTDATA_FILENAME2));
        assertEquals("Upload successful!", fileUploadPage.getInfoMessage());
        assertEquals(2, fileUploadPage.getFileTableSize());

        uploadFile(TESTDATA_LOCAL_FILEPATH);
        assertEquals(2, fileUploadPage.getFileTableSize());
        assertEquals("File with filename " + TESTDATA_FILENAME + " exists.", fileUploadPage.getErrorMessage());

        fileUploadPage.clickDeleteBtn(TESTDATA_FILENAME2);
        assertFalse(fileUploadPage.existsFileEntry(TESTDATA_FILENAME2));
        assertEquals("File has been deleted!", fileUploadPage.getInfoMessage());
        assertEquals(1, fileUploadPage.getFileTableSize());
    }
}
