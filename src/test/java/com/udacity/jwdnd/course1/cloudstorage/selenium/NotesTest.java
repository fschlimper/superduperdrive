package com.udacity.jwdnd.course1.cloudstorage.selenium;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotesPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTest extends CloudStorageBaseTest {

    private final static String TESTDATA_NOTE_TITLE = "Stories of interest";
    private final static String TESTDATA_NOTE_TITLE_2 = "More stories";
    private final static String TESTDATA_NOTE_DESCRIPTION = "Really interesting content";
    private final static String TESTDATA_NOTE_DESCRIPTION_2 = "Really interesting content 2";

    @Autowired
    private NoteMapper noteMapper;

    @AfterEach
    public void deleteAllNotes() {
        noteMapper.deleteAllNotes();
    }

    @Test
    public void createNotes() {
        signupAndLogin();
        NotesPage notesPage = createNotesPage();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("showNoteModalBtn")));

        notesPage.addNote(TESTDATA_NOTE_TITLE, TESTDATA_NOTE_DESCRIPTION, wait);
        assertEquals("Note created.", notesPage.getInfoMessage());
        assertEquals(1, notesPage.countNoteEntry(TESTDATA_NOTE_TITLE));

        notesPage.addNote(TESTDATA_NOTE_TITLE, TESTDATA_NOTE_DESCRIPTION, wait);
        assertEquals("Note with this title already exists.", notesPage.getErrMessage());
        assertEquals(1, notesPage.countNoteEntry(TESTDATA_NOTE_TITLE));

        notesPage.addNote(TESTDATA_NOTE_TITLE_2, TESTDATA_NOTE_DESCRIPTION, wait);
        assertEquals("Note created.", notesPage.getInfoMessage());
        assertEquals(1, notesPage.countNoteEntry(TESTDATA_NOTE_TITLE));
        assertEquals(1, notesPage.countNoteEntry(TESTDATA_NOTE_TITLE_2));
    }

    @Test
    public void deleteNote() {
        signupAndLogin();
        NotesPage notesPage = createNotesPage();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("showNoteModalBtn")));

        notesPage.addNote(TESTDATA_NOTE_TITLE, TESTDATA_NOTE_DESCRIPTION, wait);
        assertEquals("Note created.", notesPage.getInfoMessage());
        assertEquals(1, notesPage.countNoteEntry(TESTDATA_NOTE_TITLE));

        notesPage.deleteNote(TESTDATA_NOTE_TITLE);
        assertEquals("Note deleted.", notesPage.getInfoMessage());
        assertEquals(0, notesPage.countNoteEntry(TESTDATA_NOTE_TITLE));
    }

    @Test
    public void updateNote() {
        signupAndLogin();
        NotesPage notesPage = createNotesPage();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("showNoteModalBtn")));

        notesPage.addNote(TESTDATA_NOTE_TITLE, TESTDATA_NOTE_DESCRIPTION, wait);

        notesPage.updateNote(TESTDATA_NOTE_TITLE, TESTDATA_NOTE_TITLE_2, TESTDATA_NOTE_DESCRIPTION_2, wait);
        assertEquals(TESTDATA_NOTE_DESCRIPTION_2, notesPage.getDescriptionByTitle(TESTDATA_NOTE_TITLE_2));

        notesPage.addNote(TESTDATA_NOTE_TITLE, TESTDATA_NOTE_DESCRIPTION, wait);
        notesPage.updateNote(TESTDATA_NOTE_TITLE, TESTDATA_NOTE_TITLE_2, TESTDATA_NOTE_DESCRIPTION_2, wait);
        assertEquals("Note with this title already exists.", notesPage.getErrMessage());
    }

}
