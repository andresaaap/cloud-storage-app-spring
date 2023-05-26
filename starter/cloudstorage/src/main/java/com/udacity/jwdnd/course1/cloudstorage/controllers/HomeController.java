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
    public String homeView(Authentication authentication, NoteForm noteForm, CredentialForm credentialForm, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        // add to model an attibute called notes with the value of noteService.getNotes()
        model.addAttribute("notes", noteService.getNotes(currentUser.getUserid()));
        model.addAttribute("credentials", credentialService.getCredentials(currentUser.getUserid()));
        model.addAttribute("files", fileService.getFiles(currentUser.getUserid()));
        return "home";
    }

    //PostMapping for adding a note
    @PostMapping("/notes/add")
    public String addNote(Authentication authentication, NoteForm noteForm, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        noteForm.setUserid(currentUser.getUserid());
        noteService.addNote(noteForm);
        model.addAttribute("notes", noteService.getNotes(currentUser.getUserid()));
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));
        // On successful note addition, add a success message to the model
        model.addAttribute("successNoteAction", "Note added successfully!");
        return "home";
    }

    //PutMapping for updating a note using the endpoint /notes/update/{noteid}
    @GetMapping("/notes/update")
    public String updateNoteById(Authentication authentication, NoteForm noteForm, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        noteService.updateNoteById(noteForm);
        model.addAttribute("notes", noteService.getNotes(currentUser.getUserid()));
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));
        // On successful note addition, add a success message to the model
        model.addAttribute("successNoteAction", "Note updated successfully!");

        return "home";
    }

    //Delete mapping for deleting a note by id
    @GetMapping("/notes/delete/{noteid}")
    public String deleteNoteById(Authentication authentication, @PathVariable Integer noteid, NoteForm noteForm, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        noteService.deleteNoteById(noteid);
        model.addAttribute("notes", noteService.getNotes(currentUser.getUserid()));
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));
        // On successful note deletion, add a success message to the model
        model.addAttribute("successNoteAction", "Note deleted successfully!");
        return "home";
    }

    // PostMapping for adding a credential
    @PostMapping("/credentials/add")
    public String addCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        if(credentialForm.getCredentialId() != null) {
            credentialService.updateCredential(credentialForm);
            model.addAttribute("credentials", credentialService.getCredentials(currentUser.getUserid()));
            // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
            model.addAttribute("noteForm", new NoteForm(null, null, null, null));
            // on successful credential update, add a success message to the model
            model.addAttribute("successCredentialAction", "Credential updated successfully!");
        }
        else {

            credentialForm.setUserId(currentUser.getUserid());
            try {
                credentialService.addCredential(credentialForm);
                model.addAttribute("successCredentialAction", "Credential added successfully!");
            }
            catch (Exception e) {
                model.addAttribute("errorCredentialAction", e.getMessage());
            }
            model.addAttribute("credentials", credentialService.getCredentials(currentUser.getUserid()));
            // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
            model.addAttribute("noteForm", new NoteForm(null, null, null, null));
        }

        return "home";
    }

    // GetMapping for deleting a credential by id
    @GetMapping("/credentials/delete/{credentialid}")
    public String deleteCredentialById(Authentication authentication, @PathVariable Integer credentialid, CredentialForm credentialForm, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        credentialService.deleteCredential(credentialid);
        model.addAttribute("credentials", credentialService.getCredentials(currentUser.getUserid()));
        // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
        model.addAttribute("noteForm", new NoteForm(null, null, null, null));
        // on successful credential deletion, add a success message to the model
        model.addAttribute("successCredentialAction", "Credential deleted successfully!");
        // on successful credential deletion, add a success message to the model
        model.addAttribute("successCredentialAction", "Credential deleted successfully!");

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
        try{
            // add the file
            fileService.addFile(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), currentUser.getUserid(), file.getBytes()));
            // on successful file upload, add a success message to the model
            model.addAttribute("successFileAction", "File uploaded successfully!");

        }
        catch (Exception e) {
            model.addAttribute("errorFileAction", e.getMessage());
        }
        // add attribute to model called files with the value of fileService.getFiles()
        model.addAttribute("files", fileService.getFiles(currentUser.getUserid()));
        // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
        model.addAttribute("noteForm", new NoteForm(null, null, null, null));
        // add attribute to model called credentialForm with the value of new CredentialForm() initialized with null values
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));

        return "home";
    }

    // GetMapping for deleting a file by id
    @GetMapping("/files/delete/{fileid}")
    public String deleteFileById(Authentication authentication, @PathVariable Integer fileid, Model model) {
        User currentUser = userService.getUser(authentication.getName());
        fileService.deleteFile(fileid);
        // add attribute to model called files with the value of fileService.getFiles()
        model.addAttribute("files", fileService.getFiles(currentUser.getUserid()));
        // add attribute to model called noteForm with the value of new NoteForm() initialized with null values
        model.addAttribute("noteForm", new NoteForm(null, null, null, null));
        // add attribute to model called credentialForm with the value of new CredentialForm() initialized with null values
        model.addAttribute("credentialForm", new CredentialForm(null, null, null, null, null));
        // on successful file deletion, add a success message to the model
        model.addAttribute("successFileAction", "File deleted successfully!");

        return "home";
    }

    @GetMapping("/files/download/{fileid}")
    public ResponseEntity downloadFileById(Authentication authentication, @PathVariable Integer fileid, Model model){
        User currentUser = userService.getUser(authentication.getName());
        File file = fileService.getFile(fileid);
        model.addAttribute("files", fileService.getFiles(currentUser.getUserid()));
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
