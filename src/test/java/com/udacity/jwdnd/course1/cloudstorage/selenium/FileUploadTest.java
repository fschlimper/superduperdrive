package com.udacity.jwdnd.course1.cloudstorage.selenium;

import com.udacity.jwdnd.course1.cloudstorage.pages.FileUploadPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadTest extends CloudStorageBaseTest {

    public static final String TESTDATA_FILENAME = "holy_grail.jpg";
    public static final String TESTDATA_FILENAME2 = "holy_grail2.jpg";
    public static final String TESTDATA_LOCAL_FILEPATH = System.getProperty("user.dir") + "/src/test/resources/" + TESTDATA_FILENAME;
    public static final String TESTDATA_LOCAL_FILEPATH2 = System.getProperty("user.dir") + "/src/test/resources/" + TESTDATA_FILENAME2;

    @Test
    public void testFileUpload() {
        SignupPage signupPage = createSignupPage();
        signupPage.createNewUser(TESTDATA_FIRSTNAME, TESTDATA_LASTNAME, TESTDATA_USERNAME, TESTDATA_PASSWORD);
        LoginPage loginPage = createLoginPage();
        loginPage.login(TESTDATA_USERNAME, TESTDATA_PASSWORD);
        FileUploadPage fileUploadPage = createHomePage();
        fileUploadPage.uploadFile(TESTDATA_LOCAL_FILEPATH);
        assertTrue(fileUploadPage.existsFileEntry(TESTDATA_FILENAME));
        assertEquals("Upload successful!", fileUploadPage.getInfoMessage());
        assertEquals(1, fileUploadPage.getFileTableSize());

        fileUploadPage.uploadFile(TESTDATA_LOCAL_FILEPATH2);
        assertTrue(fileUploadPage.existsFileEntry(TESTDATA_FILENAME2));
        assertEquals("Upload successful!", fileUploadPage.getInfoMessage());
        assertEquals(2, fileUploadPage.getFileTableSize());

        fileUploadPage.uploadFile(TESTDATA_LOCAL_FILEPATH);
        assertEquals(2, fileUploadPage.getFileTableSize());
        assertEquals("File with filename " + TESTDATA_FILENAME + " exists.", fileUploadPage.getErrorMessage());

        fileUploadPage.clickDeleteBtn(TESTDATA_FILENAME2);
        assertFalse(fileUploadPage.existsFileEntry(TESTDATA_FILENAME2));
        assertEquals("File has been deleted!", fileUploadPage.getInfoMessage());
        assertEquals(1, fileUploadPage.getFileTableSize());
    }
}
