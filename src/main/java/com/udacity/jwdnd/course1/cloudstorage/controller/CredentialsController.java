package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CredentialsController {

    private final CredentialsService credentialsService;
    private final UserService userService;

    public CredentialsController(CredentialsService credentialsService, UserService userService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
    }

    @GetMapping("credentials")
    public String getCredentials(Model model) {

        User user = getLoggedInUser();

        if (user == null || user.getUserid() == null) {
            return "login";
        }

        model.addAttribute("credentialList", credentialsService.getCredentialsByUserId(user.getUserid()));
        clearMessages(model);

        model.addAttribute("activeTab", "credentials");

        return "home";
    }

    @PostMapping("credentials/add")
    public String addOrUpdateCredentials(
            @RequestParam("credentialId") Integer credentialId,
            @RequestParam("url") String url,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        User user = getLoggedInUser();

        if (user == null || user.getUserid() == null) {
            return "login";
        }

        clearMessages(model);

        Credentials credentials = new Credentials();
        credentials.setCredentialid(credentialId);
        credentials.setUrl(url);
        credentials.setUsername(username);
        credentials.setClearPassword(password);
        credentials.setUserid(user.getUserid());

        String errorMsg = credentialsService.addOrUpdateCredentials(credentials);

        if (errorMsg == null) {
            setInfoMessage("Credentials created.", model);
        } else  {
            setErrorMessage(errorMsg, model);
        }

        List<Credentials> credentialsList = credentialsService.getCredentialsByUserId(getLoggedInUser().getUserid());

        model.addAttribute("credentialList", credentialsList);

        model.addAttribute("activeTab", "credentials");
        return "home";
    }

    @GetMapping("credentials/delete")
    public String deleteCredentials(@RequestParam("credentialId") Integer credentialId, Model model) {
        clearMessages(model);
        String errMsg = credentialsService.deleteCredential(credentialId);
        if (errMsg != null) {
            setErrorMessage(errMsg, model);
        } else {
            setInfoMessage("Credentials deleted.", model);
        }

        List<Credentials> credentialsList = credentialsService.getCredentialsByUserId(getLoggedInUser().getUserid());

        model.addAttribute("credentialList", credentialsList);

        model.addAttribute("activeTab", "credentials");

        return "home";
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

    private User getLoggedInUser() {
        return userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
