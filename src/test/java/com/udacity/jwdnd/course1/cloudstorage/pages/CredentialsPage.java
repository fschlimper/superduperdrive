package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialsPage {

    private final WebDriver webDriver;

    @FindBy(id="showCredModalBtn")
    WebElement showCredModalBtn;

    @FindBy(id="credential-url")
    WebElement credUrlInupt;

    @FindBy(id="credential-username")
    WebElement credUsernameInput;

    @FindBy(id="credential-password")
    WebElement credPasswordInput;

    @FindBy(id="credSubmitBtn")
    WebElement credSubmitBtn;

    @FindBy(id="infoMsg")
    WebElement infoMsg;

    @FindBy(id="errorMsg")
    WebElement errorMsg;

    public CredentialsPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void addCredentials(String url, String username, String password, WebDriverWait wait) {
        showCredModalBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        credUrlInupt.sendKeys(url);
        credUsernameInput.sendKeys(username);
        credPasswordInput.sendKeys(password);
        credSubmitBtn.click();
    }

    public void deleteCredentials(String url) {
        WebElement deleteBtn = webDriver.findElement(By.xpath("//table[@id='credentialTable']/tbody/tr[th='" + url + "']/td[1]/a"));
        deleteBtn.click();
    }

    public void updateCredentialPassword(String url, String password, WebDriverWait wait) {
        WebElement updateBtn = webDriver.findElement(By.xpath("//table[@id='credentialTable']/tbody/tr[th='" + url + "']/td[1]/button"));
        updateBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        credPasswordInput.sendKeys(password);
        credSubmitBtn.click();
    }

    public long countCredentialEntries(String url) {
        return webDriver.findElements(By.xpath("//table[@id='credentialTable']/tbody/tr/th"))
                .stream()
                .map(WebElement::getText)
                .filter(url::contentEquals)
                .count();
    }

    public String getPassword(String url) {
        return webDriver.findElement(By.xpath("//table[@id='credentialTable']/tbody/tr[th='" + url + "']/td[3]")).getText();
    }

    public String getInfoMessage() {
        return infoMsg.getText();
    }

    public String getErrorMessage() {
        return errorMsg.getText();
    }
}
