package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id="signupSuccessMsg")
    private WebElement signupSuccessMsg;

    @FindBy(id="signupErrorMsg")
    private WebElement signupErrorMsg;

    @FindBy(id="inputFirstName")
    private WebElement inputFirstNameField;

    @FindBy(id="inputLastName")
    private WebElement inputLastNameField;

    @FindBy(id="inputUsername")
    private WebElement inputUsernameField;

    @FindBy(id="inputPassword")
    private WebElement inputPasswordField;

    @FindBy(id="submitBtn")
    private WebElement submitBtn;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void createNewUser(String firstName, String lastName, String username, String password) {
        inputFirstNameField.sendKeys(firstName);
        inputLastNameField.sendKeys(lastName);
        inputUsernameField.sendKeys(username);
        inputPasswordField.sendKeys(password);
        submitBtn.click();
    }

    public String getErrorMsg() {
        return signupErrorMsg.getText();
    }

    public String getSuccessMsg() {
        return signupSuccessMsg.getText();
    }

}
