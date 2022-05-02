package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UploadFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class FileController {

    private FileUploadService fileUploadService;
    private UserService userService;

    public FileController(FileUploadService fileUploadService, UserService userService) {
        this.fileUploadService = fileUploadService;
        this.userService = userService;
    }

    @GetMapping("files")
    public String getHome(Model model) {
        // If there is no user, go back to login
        if (getLoggedInUser() == null || getLoggedInUser().getUserid() == null) {
            return "login";
        }
        model.addAttribute("files", fileUploadService.getFilesByUserId(getLoggedInUser().getUserid()));
        model.addAttribute("activeTab", "files");
        clearMessages(model);

        return "home";
    }

    @PostMapping("files/add")
    public String addFile(@RequestParam("inputFileName") MultipartFile fileUpload, Model model) {
        User loggedInUser = getLoggedInUser();

        try {
            UploadFile uploadFile =
                    new UploadFile(
                            fileUpload.getOriginalFilename(),
                            fileUpload.getContentType(),
                            String.valueOf(fileUpload.getSize()),
                            loggedInUser.getUserid(),
                            fileUpload.getInputStream());
            String errorMsg = fileUploadService.uploadFile(uploadFile, loggedInUser.getUserid());
            if (errorMsg != null) {
                setErrorMessage(errorMsg, model);
            } else {
                setInfoMessage("Upload successful!", model);
            }
        } catch (IOException e) {
            // TODO
        }
        model.addAttribute("files", fileUploadService.getFilesByUserId(getLoggedInUser().getUserid()));
        model.addAttribute("activeTab", "files");
        return "home";
    }

    @GetMapping("files/delete")
    public String deleteFile(@RequestParam("fileId") String fileId, Model model) {

        String errorMsg = fileUploadService.deleteFile(Integer.parseInt(fileId));

        if (errorMsg != null) {
            setErrorMessage(errorMsg, model);
        } else {
            setInfoMessage("File has been deleted!", model);
        }

        model.addAttribute("files", fileUploadService.getFilesByUserId(getLoggedInUser().getUserid()));
        model.addAttribute("activeTab", "files");
        return "home";
    }

    @GetMapping("files/show")
    public ResponseEntity<InputStreamResource> showFile(@RequestParam("fileId") String fileId, Model model) {
        UploadFile uploadFile = fileUploadService.getFileByFileId(Integer.parseInt(fileId));
        return ResponseEntity
                .ok()
                .contentLength(Long.parseLong(uploadFile.getFilesize()))
                .contentType(
                        MediaType.parseMediaType(uploadFile.getContenttype()))
                .body(new InputStreamResource(uploadFile.getFiledata()));

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
