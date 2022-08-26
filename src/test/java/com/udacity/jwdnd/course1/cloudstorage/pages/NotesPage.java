package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotesPage {

    @FindBy(id="nav-notes-tab")
    private WebElement navNotesBtn;

    @FindBy(id="showNoteModalBtn")
    private WebElement showNoteModalBtn;

    @FindBy(id="note-title")
    private WebElement noteTitleInput;

    @FindBy(id="note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id="noteSubmit")
    private  WebElement addNoteBtn;

    @FindBy(id="infoMsg")
    private WebElement infoMsg;

    @FindBy(id="errorMsg")
    private WebElement errorMsg;

    private WebDriver driver;

    public NotesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        navNotesBtn.click();
    }

    public void addNote(String title, String description, WebDriverWait wait) {
        showNoteModalBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.sendKeys(description);
        addNoteBtn.submit();
    }

    public void updateNote(String oldTitle, String newTitle, String description, WebDriverWait wait) {
        WebElement updateBtn = driver.findElement(By.xpath("//table[@id='notesTable']/tbody/tr[th='" + oldTitle + "']/td[1]/button"));
        updateBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        if (newTitle != null) {
            noteTitleInput.clear();
            noteTitleInput.sendKeys(newTitle);
        }
        if (description != null) {
            noteDescriptionInput.clear();
            noteDescriptionInput.sendKeys(description);
        }
        addNoteBtn.submit();
    }

    public void deleteNote(String title) {
        WebElement deleteBtn = driver.findElement(By.xpath("//table[@id='notesTable']/tbody/tr[th='" + title + "']/td[1]/a"));
        //WebElement deleteBtn = driver.findElement(By.xpath("//table[@id='notesTable']/tbody/tr[td[x2]='" + title + "']/td[1]"));
        deleteBtn.click();
    }

    public long countNoteEntry(String noteTitle) {
        return driver.findElements(By.xpath("//table[@id='notesTable']/tbody/tr/th"))
                .stream()
                .map(WebElement::getText)
                .filter(noteTitle::contentEquals)
                .count();
    }

    public String getDescriptionByTitle(String title) {
        return driver.findElement(By.xpath("//table[@id='notesTable']/tbody/tr[th='" + title + "']/td[2]")).getText();
    }

    public String getInfoMessage() {
        return infoMsg.getText();
    }

    public String getErrMessage() {
        return errorMsg.getText();
    }
}
