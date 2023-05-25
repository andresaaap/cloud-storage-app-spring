package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController implements HandlerExceptionResolver {
    // private variable for the NoteService
    private NoteService noteService;
    private UserService userService;

    // create credential service variable
    private CredentialService credentialService;

    // file service variable
    private FileService fileService;

    // encruption service variable
    private EncryptionService encryptionService;

    // constructor for HomeController
    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService, FileService fileService, EncryptionService encryptionService) {

        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("")
    public String homeView(NoteForm noteForm, CredentialForm credentialForm, Model model) {
        // add to model an attibute called notes with the value of noteService.getNotes()
        model.addAttribute("notes", noteService.getNotes());
        model.addAttribute("credentials", credentialService.getCredentials());
        model.addAttribute("files", fileService.getFiles());
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

    @RequestMapping(value = "credentials/decrypt/{credentialid}", method = RequestMethod.GET)
    public String signup(@PathVariable Integer credentialid, Model model) {
        // get credential by id
        Credential credential = credentialService.getCredential(credentialid);
        // decrypt password
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        // add decrypted password to model
        //model.addAttribute("decryptedPassword", decryptedPassword);

        model.addAttribute("credentialForm", new CredentialForm(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), decryptedPassword, credential.getUserId()));

        return "home :: #credentialForm";

    }


    // PostMapping for adding a file
    @PostMapping("/files/add")
    public String addFile(Authentication authentication, Model model, @RequestParam("fileUpload") MultipartFile file) throws IOException {
        User currentUser = userService.getUser(authentication.getName());
        // add the file
        fileService.addFile(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), currentUser.getUserid(), file.getBytes()));
        // add attribute to model called files with the value of fileService.getFiles()
        model.addAttribute("files", fileService.getFiles());
        // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
        model.addAttribute("noteForm", new NoteForm(null, null, null, null));
        // add attribute to model called credentialForm with the value of new CredentialForm() initialized with null values
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));

        return "home";
    }

    // GetMapping for deleting a file by id
    @GetMapping("/files/delete/{fileid}")
    public String deleteFileById(@PathVariable Integer fileid, Model model) {
        fileService.deleteFile(fileid);
        // add attribute to model called files with the value of fileService.getFiles()
        model.addAttribute("files", fileService.getFiles());
        // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
        model.addAttribute("noteForm", new NoteForm(null, null, null, null));
        // add attribute to model called credentialForm with the value of new CredentialForm() initialized with null values
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));

        return "home";
    }

    @GetMapping("/files/download/{fileid}")
    public ResponseEntity downloadFileById(@PathVariable Integer fileid, Model model){
        File file = fileService.getFile(fileid);
        model.addAttribute("files", fileService.getFiles());
        // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
        model.addAttribute("noteForm", new NoteForm(null, null, null, null));
        // add attribute to model called credentialForm with the value of new CredentialForm() initialized with null values
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"").body(file.getFileData());
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exc) {
        ModelAndView modelAndView = new ModelAndView("file-size-exceeds");
        if (exc instanceof MaxUploadSizeExceededException) {
            modelAndView.getModel().put("message", "File size exceeds limit!");
        }
        return modelAndView;
    }


}
