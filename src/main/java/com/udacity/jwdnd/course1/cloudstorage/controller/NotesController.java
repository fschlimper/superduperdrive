package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotesController {

    private UserService userService;
    private NoteService notesService;

    public NotesController(UserService userService, NoteService notesService) {
        this.userService = userService;
        this.notesService = notesService;
    }

    @GetMapping("notes")
    public String getNotes(Model model) {
        // If there is no user, go back to login
        if (getLoggedInUser() == null || getLoggedInUser().getUserid() == null) {
            return "login";
        }
        model.addAttribute("notes", notesService.getNotesByUserId(getLoggedInUser().getUserid()));
        clearMessages(model);

        model.addAttribute("activeTab", "notes");

        return "home";
    }

    @PostMapping("notes/add")
    public String addOrUpdateNote(
            @RequestParam("noteId") Integer noteId,
            @RequestParam("noteTitle") String noteTitle,
            @RequestParam("noteDescription") String noteDescription,
            Model model) {

        clearMessages(model);
        String errMsg = notesService.addOrUpdateNote(new Note(noteId, noteTitle, noteDescription, getLoggedInUser().getUserid()));
        if (errMsg == null) {
            setInfoMessage("Note created.", model);
        } else {
            setErrorMessage(errMsg, model);
        }
        model.addAttribute("notes", notesService.getNotesByUserId(getLoggedInUser().getUserid()));
        model.addAttribute("activeTab", "notes");

        return "home";
    }

    @GetMapping("notes/delete")
    public String deleteNote(@RequestParam("noteId") Integer noteId, Model model) {
        clearMessages(model);
        String errMsg = notesService.deleteNote(noteId);
        if (errMsg == null) {
            setInfoMessage("Note deleted.", model);
        } else {
            setErrorMessage(errMsg, model);
        }
        model.addAttribute("notes", notesService.getNotesByUserId(getLoggedInUser().getUserid()));
        model.addAttribute("activeTab", "notes");

        return "home";
    }

    private User getLoggedInUser() {
        return userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private void setErrorMessage(String message, Model model) {
        model.addAttribute("alertDanger", message);
        model.addAttribute("alertInfo", null);
    }

    private void setInfoMessage(String message, Model model) {
        model.addAttribute("alertDanger", null);
        model.addAttribute("alertInfo", message);
    }

    private void clearMessages(Model model) {
        model.addAttribute("alertDanger", null);
        model.addAttribute("alertInfo", null);
    }
}
