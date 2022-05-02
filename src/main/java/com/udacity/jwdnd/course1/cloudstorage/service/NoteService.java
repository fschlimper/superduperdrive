package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public String addOrUpdateNote(Note note) {
        if (note.getNoteid() == null) {
            return addNote(note);
        } else {
            return updateNote(note);
        }
    }

    private String addNote(Note note) {
        if (noteMapper.getNoteByTitle(note.getTitle(), note.getUserid()) != null) {
            return "Note with this title already exists.";
        }
        noteMapper.addNote(note);
        return null;
    }

    private String updateNote(Note note) {
        Note noteWithSameTitle = noteMapper.getNoteByTitle(note.getTitle(), note.getUserid());
        if (noteWithSameTitle != null && !noteWithSameTitle.getNoteid().equals(note.getNoteid())) {
            return "Note with this title already exists.";
        }
        noteMapper.updateNote(note);
        return null;
    }

    public String deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
        return null;
    }

    public List<Note> getNotesByUserId(Integer userId) {
        return noteMapper.getNotesByUserId(userId);
    }
}
