package com.udacity.jwdnd.course1.cloudstorage.selenium;

import com.udacity.jwdnd.course1.cloudstorage.pages.FileUploadPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotesPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
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
//        webDriver.quit();
//        webDriver = null;
    }

    @BeforeEach
    public void beforeEach() {
        baseUrl = "http://localhost:" + port;
    }

    protected SignupPage createSignupPage() {
        webDriver.get(baseUrl + "/signup");
        return new SignupPage(webDriver);
    }

    protected LoginPage createLoginPage() {
        webDriver.get(baseUrl + "/login");
        return new LoginPage(webDriver);
    }

    protected FileUploadPage createHomePage() {
        webDriver.get(baseUrl + "/files");
        return new FileUploadPage(webDriver);
    }

    protected NotesPage createNotesPage() {
        webDriver.get(baseUrl + "/files");
        return new NotesPage(webDriver);
    }
}
