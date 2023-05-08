package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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

    private CredentialService credentialService;

    // constructor for HomeController
    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService) {

        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping("")
    public String homeView(NoteForm noteForm, CredentialForm credentialForm, Model model) {
        // add to model an attibute called notes with the value of noteService.getNotes()
        model.addAttribute("notes", noteService.getNotes());
        model.addAttribute("credentials", credentialService.getCredentials());
        return "home";
    }

    //PostMapping for adding a note
    @PostMapping("/notes/add")
    public String addNote(Authentication authentication, NoteForm noteForm, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        noteForm.setUserid(currentUser.getUserid());
        noteService.addNote(noteForm);
        model.addAttribute("notes", noteService.getNotes());
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));
        return "home";
    }

    //PutMapping for updating a note using the endpoint /notes/update/{noteid}
    @GetMapping("/notes/update")
    public String updateNoteById(NoteForm noteForm, Model model) {
        noteService.updateNoteById(noteForm);
        model.addAttribute("notes", noteService.getNotes());
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));

        return "home";
    }

    //Delete mapping for deleting a note by id
    @GetMapping("/notes/delete/{noteid}")
    public String deleteNoteById(@PathVariable Integer noteid, NoteForm noteForm, Model model) {
        noteService.deleteNoteById(noteid);
        model.addAttribute("notes", noteService.getNotes());
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));

        return "home";
    }

    // PostMapping for adding a credential
    @PostMapping("/credentials/add")
    public String addCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        if(credentialForm.getCredentialId() != null) {
            credentialService.updateCredential(credentialForm);
            model.addAttribute("credentials", credentialService.getCredentials());
            // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
            model.addAttribute("noteForm", new NoteForm(null, null, null, null));
        }
        else {
            User currentUser = userService.getUser(authentication.getName());
            credentialForm.setUserId(currentUser.getUserid());
            credentialService.addCredential(credentialForm);
            model.addAttribute("credentials", credentialService.getCredentials());
            // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
            model.addAttribute("noteForm", new NoteForm(null, null, null, null));
        }

        return "home";
    }

    // GetMapping for deleting a credential by id
    @GetMapping("/credentials/delete/{credentialid}")
    public String deleteCredentialById(@PathVariable Integer credentialid, CredentialForm credentialForm, Model model) {
        credentialService.deleteCredential(credentialid);
        model.addAttribute("credentials", credentialService.getCredentials());
        // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
        model.addAttribute("noteForm", new NoteForm(null, null, null, null));

        return "home";
    }


}
