package com.pce.notesapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.pce.notesapp.model.Note
import com.pce.notesapp.repository.dao.NotesDao
import com.pce.notesapp.repository.database.NotesDatabase

class NotesRepository(context: Context) {
    private val dao: NotesDao = NotesDatabase(context).notesDao()

    fun insert(note: Note) = dao.insertNote(note)

    fun update(note: Note) = dao.updateNote(note)

    fun delete(note: Note) = dao.deleteNote(note)

    fun deleteAll() = dao.deleteAllNotes()

    fun selectAll(): LiveData<List<Note>> = dao.selectAllNotes()
}