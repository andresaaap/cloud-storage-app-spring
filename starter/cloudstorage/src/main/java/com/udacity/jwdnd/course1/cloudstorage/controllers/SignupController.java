package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    // create UserService private variable called userService
    private UserService userService;

    // create constructor for SignupController
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    // create PostMapping to create a new user
    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;
        // check if username is available
        if (!userService.checkUsername(user.getUsername())) {
            // if username is not available, return to signup page
            signupError = "Username already exists. Please choose a different username.";
        }
        else {
            // if username is available, create user
            int rowsAdded = userService.createUser(user);
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
