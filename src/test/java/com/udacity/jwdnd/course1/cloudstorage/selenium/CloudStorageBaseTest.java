package com.udacity.jwdnd.course1.cloudstorage.selenium;

import com.udacity.jwdnd.course1.cloudstorage.pages.FileUploadPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.tomcat.jni.Directory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CloudStorageBaseTest {

    public static final String TESTDATA_FIRSTNAME = "Dr.";
    public static final String TESTDATA_LASTNAME = "Who";
    public static final String TESTDATA_USERNAME = "drwho";
    public static final String TESTDATA_PASSWORD = "tardis";
    public static final String TESTDATA_FILENAME = "holy_grail.jpg";
    public static final String TESTDATA_FILENAME2 = "holy_grail2.jpg";
    public static final String TESTDATA_LOCAL_FILEPATH = System.getProperty("user.dir") + "/src/test/resources/" + TESTDATA_FILENAME;
    public static final String TESTDATA_LOCAL_FILEPATH2 = System.getProperty("user.dir") + "/src/test/resources/" + TESTDATA_FILENAME2;

    @LocalServerPort
    protected Integer port;

    protected static WebDriver webDriver;

    protected String baseUrl;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        webDriver.quit();
        webDriver = null;
    }

    @BeforeEach
    public void beforeEach() {
        baseUrl = "http://localhost:" + port;
    }

    protected SignupPage signupUser(String firstname, String lastname, String username, String password) {
        webDriver.get(baseUrl + "/signup");
        SignupPage signupPage = new SignupPage(webDriver);
        signupPage.createNewUser(firstname, lastname, username, password);

        return signupPage;
    }

    protected LoginPage loginUser(String username, String password) {
        webDriver.get(baseUrl + "/login");
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login(username, password);

        return loginPage;
    }

    protected FileUploadPage uploadFile(String filename) {
        webDriver.get(baseUrl + "/files");
        FileUploadPage fileUploadPage = new FileUploadPage(webDriver);
        System.out.println("Dir: " + System.getProperty("user.dir"));
        fileUploadPage.uploadFile(filename);

        return fileUploadPage;
    }
}
