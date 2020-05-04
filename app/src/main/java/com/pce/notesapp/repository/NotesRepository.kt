package com.pce.notesapp.repository

import android.content.Context
import com.pce.notesapp.model.Note
import com.pce.notesapp.repository.dao.NotesDao
import com.pce.notesapp.repository.database.NotesDatabase
import kotlinx.coroutines.SupervisorJob

class NotesRepository(private val context: Context) {
    private val dao: NotesDao by lazy { NotesDatabase(context).notesDao() }

    private val job = SupervisorJob()

    fun insert(note: Note) = dao.insertNote(note)

    fun update(note: Note) = dao.updateNote(note)

    fun delete(note: Note) = dao.deleteNote(note)

    fun deleteAll() = dao.deleteAllNotes()

    fun selectAll() = dao.selectAllNotes()
}