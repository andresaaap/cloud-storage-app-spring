package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
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
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return new RedirectView("/login", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        redirectAttributes.addFlashAttribute("signupError", signupError);
        return new RedirectView("/signup", true);
    }
}
