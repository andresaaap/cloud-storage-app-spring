package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    // private variable for the NoteService
    private NoteService noteService;
    private UserService userService;

    // constructor for HomeController
    public HomeController(NoteService noteService, UserService userService) {

        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("")
    public String homeView(NoteForm noteForm, Model model) {
        // add to model an attibute called notes with the value of noteService.getNotes()
        model.addAttribute("notes", noteService.getNotes());
        return "home";
    }

    //PostMapping for adding a note
    @PostMapping("/notes/add")
    public String addNote(Authentication authentication, NoteForm noteForm, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        noteForm.setUserid(currentUser.getUserid());
        noteService.addNote(noteForm);
        model.addAttribute("notes", noteService.getNotes());
        return "home";
    }

    //PutMapping for updating a note using the endpoint /notes/update/{noteid}
    @GetMapping("/notes/update")
    public String updateNoteById(NoteForm noteForm, Model model) {
        noteService.updateNoteById(noteForm);
        model.addAttribute("notes", noteService.getNotes());
        return "home";
    }

    //Delete mapping for deleting a note by id
    @GetMapping("/notes/delete/{noteid}")
    public String deleteNoteById(@PathVariable Integer noteid, NoteForm noteForm, Model model) {
        noteService.deleteNoteById(noteid);
        model.addAttribute("notes", noteService.getNotes());
        return "home";
    }


}
