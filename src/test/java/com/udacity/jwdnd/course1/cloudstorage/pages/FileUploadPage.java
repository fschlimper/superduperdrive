package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FileUploadPage {

    @FindBy(id="errorMsg")
    private WebElement errorMsg;

    @FindBy(id="infoMsg")
    private WebElement infoMsg;

    @FindBy(id="inputFileName")
    private WebElement inputFileName;

    @FindBy(id="fileUploadBtn")
    private WebElement fileUploadBtn;

    @FindBy(id="nav-notes")
    private WebElement navNotesBtn;

    WebDriver driver;

    public FileUploadPage(WebDriver webDriver) {
        driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void uploadFile(String filename) {
        inputFileName.sendKeys(filename);
        fileUploadBtn.click();
    }

    public String getInfoMessage() {
        return infoMsg.getText();
    }

    public String getErrorMessage() {
        return errorMsg.getText();
    }

    public boolean existsFileEntry(String filename) {
        for (WebElement elem: driver.findElements(By.xpath("//table[@id='fileTable']/tbody/tr/td[2]"))) {
            if (filename.contentEquals(elem.getText())) {
                return true;
            }
        }
        return false;
    }

    public void clickDeleteBtn(String filename) {
        WebElement deleteBtn = driver.findElement(By.xpath("//table[@id='fileTable']/tbody/tr[td[2]='" + filename + "']/td[1]"));
        deleteBtn.click();
    }

    public int getFileTableSize() {
        return driver.findElements(By.xpath("//table[@id='fileTable']/tbody/tr")).size();
    }

    public void gotoNotesApp() {
        navNotesBtn.click();
    }
}
