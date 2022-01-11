package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignup(Model model) {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "Username is already taken. Please choose another one.";
        }

        if (signupError == null) {
            int noUserCreated = userService.signupUser(user);
            if (noUserCreated != 1) {
                signupError = "An unknown error occurred. Please try again";
            } else {
                model.addAttribute("signupSuccess", true);
            }
        }

        model.addAttribute("signupError", signupError);
        return "signup";
    }
}
