package com.pce.notesapp.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pce.notesapp.model.Note

@Dao
interface NotesDao {

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes_table ORDER BY priority DESC")
    fun selectAllNotes(): LiveData<List<Note>>
}