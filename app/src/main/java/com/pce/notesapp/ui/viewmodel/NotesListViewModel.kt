package com.pce.notesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pce.notesapp.model.Note
import com.pce.notesapp.repository.NotesRepository
import kotlinx.coroutines.*

class NotesListViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = NotesRepository(application)
    private val context = Dispatchers.IO + Job()
    private lateinit var notes: LiveData<List<Note>>

    init {
        runBlocking {
            notes = withContext(context) {
                repo.selectAll()
            }
        }
    }

    fun getAllNotes(): LiveData<List<Note>> = notes

    fun insert(note: Note) {
        CoroutineScope(context).launch {
            repo.insert(note)
        }
    }

    fun update(note: Note) {
        CoroutineScope(context).launch {
            repo.update(note)
        }
    }

    fun delete(note: Note) {
        CoroutineScope(context).launch {
            repo.delete(note)
        }
    }

    fun deleteAll() {
        CoroutineScope(context).launch {
            repo.deleteAll()
        }
    }
}