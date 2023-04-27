package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    // private variable to store NoteMapper
    private NoteMapper noteMapper;

    // constructor for NoteService
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;

    }

    // method to get all notes
    public List<Note> getNotes() {
        return noteMapper.getNotes();
    }

    // method to add note
    public int addNote (NoteForm noteForm) {
        Note note = new Note(null, noteForm.getNotetitle(), noteForm.getNotedescription(), noteForm.getUserid());
        // return noteMapper.insert(note);
        return noteMapper.insertNote(new Note(null, note.getNotetitle(), note.getNotedescription(), note.getUserid()));
    }
}
