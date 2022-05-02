package com.udacity.jwdnd.course1.cloudstorage.selenium;

import static org.junit.jupiter.api.Assertions.*;

import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserSignupTest extends CloudStorageBaseTest {

    @Test
    public void testUsernameNotAvailable() {
        SignupPage signupPage = createSignupPage();
        signupPage.createNewUser(TESTDATA_FIRSTNAME, TESTDATA_LASTNAME, TESTDATA_USERNAME, TESTDATA_PASSWORD);
        assertTrue(signupPage.getSuccessMsg().startsWith("You successfully signed up!"));

        signupPage.createNewUser(TESTDATA_FIRSTNAME, TESTDATA_LASTNAME, TESTDATA_USERNAME, TESTDATA_PASSWORD);
        assertEquals("Username is already taken. Please choose another one.", signupPage.getErrorMsg());
    }
}
