package com.udacity.jwdnd.course1.cloudstorage.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialsPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTest extends CloudStorageBaseTest {

    public static final String TESTDATA_CRED_URL = "https://www.garfield.com";
    public static final String TESTDATA_CRED_NAME = "Garfield";
    public static final String TESTDATA_CRED_PW = "Lasagne";

    public static final String TESTDATA_CRED_URL_2 = "https://www.odi.com";
    public static final String TESTDATA_CRED_NAME_2 = "Odi";
    public static final String TESTDATA_CRED_PW_2 = "Dogfood";

    @Test
    public void createCredentials() {
        signupAndLogin();
        CredentialsPage credentialsPage = createCredentialsPage();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showCredModalBtn")));

        credentialsPage.addCredentials(TESTDATA_CRED_URL, TESTDATA_CRED_NAME, TESTDATA_CRED_PW, wait);
        assertEquals("Credentials created.", credentialsPage.getInfoMessage());
        assertEquals(1, credentialsPage.countCredentialEntries(TESTDATA_CRED_URL));

        credentialsPage.addCredentials(TESTDATA_CRED_URL, TESTDATA_CRED_NAME, TESTDATA_CRED_PW, wait);
        assertEquals("Credentials for this webpage already exists.", credentialsPage.getErrorMessage());
        assertEquals(1, credentialsPage.countCredentialEntries(TESTDATA_CRED_URL));

        credentialsPage.addCredentials(TESTDATA_CRED_URL_2, TESTDATA_CRED_NAME_2, TESTDATA_CRED_PW_2, wait);
        assertEquals("Credentials created.", credentialsPage.getInfoMessage());
        assertEquals(1, credentialsPage.countCredentialEntries(TESTDATA_CRED_URL_2));
    }

    @Test
    public void deleteCredentials() {
        signupAndLogin();
        CredentialsPage credentialsPage = createCredentialsPage();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showCredModalBtn")));

        credentialsPage.addCredentials(TESTDATA_CRED_URL, TESTDATA_CRED_NAME, TESTDATA_CRED_PW, wait);
        assertEquals("Credentials created.", credentialsPage.getInfoMessage());
        assertEquals(1, credentialsPage.countCredentialEntries(TESTDATA_CRED_URL));

        credentialsPage.deleteCredentials(TESTDATA_CRED_URL);
        assertEquals("Credentials deleted.", credentialsPage.getInfoMessage());
        assertEquals(0, credentialsPage.countCredentialEntries(TESTDATA_CRED_URL));
    }

    @Test
    public void updateCredentials() {
        signupAndLogin();
        CredentialsPage credentialsPage = createCredentialsPage();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showCredModalBtn")));

        credentialsPage.addCredentials(TESTDATA_CRED_URL, TESTDATA_CRED_NAME, TESTDATA_CRED_PW, wait);
        assertEquals("Credentials created.", credentialsPage.getInfoMessage());
        assertEquals(1, credentialsPage.countCredentialEntries(TESTDATA_CRED_URL));
        String passwordOld = credentialsPage.getPassword(TESTDATA_CRED_URL);

        credentialsPage.updateCredentialPassword(TESTDATA_CRED_URL, TESTDATA_CRED_PW_2, wait);
        String passwordNew = credentialsPage.getPassword(TESTDATA_CRED_URL);
        assertNotEquals(passwordOld, passwordNew);
    }

}
