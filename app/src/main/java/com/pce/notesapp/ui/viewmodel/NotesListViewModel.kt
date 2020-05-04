package com.pce.notesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pce.notesapp.model.Note
import com.pce.notesapp.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NotesListViewModel(application: Application) : AndroidViewModel(application) {
    private val notes = MutableLiveData<List<Note>>()
    private val repo = NotesRepository(application)
    private val context = Dispatchers.IO + SupervisorJob()

    init {
        CoroutineScope(context).launch {
            notes.value = repo.selectAll()
        }
    }

    fun selectAll() = notes

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