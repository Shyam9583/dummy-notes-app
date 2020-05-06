package com.pce.notesapp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pce.notesapp.R
import com.pce.notesapp.model.Note
import com.pce.notesapp.ui.adapter.NotesListAdapter
import com.pce.notesapp.ui.viewmodel.NotesListViewModel
import com.pce.notesapp.ui.viewmodel.NotesListViewModelFactory

class MainActivity : AppCompatActivity() {

    private val request = 1

    private lateinit var notesListViewModel: NotesListViewModel

    private lateinit var notesListAdapter: NotesListAdapter

    private lateinit var addButton: FloatingActionButton

    private lateinit var notesList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        prepareRecyclerView()
        notesListViewModel.getAllNotes().observe(this) {
            notesListAdapter.submitList(it)
        }
        addButton.setOnClickListener {
            val insertIntent = Intent(this, NoteActivity::class.java)
            startActivityForResult(insertIntent, request)
        }
    }

    private fun bindViews() {
        notesList = findViewById(R.id.notesList)
        addButton = findViewById(R.id.addButton)
        notesListViewModel =
            ViewModelProvider(this, NotesListViewModelFactory(this.application)).get(
                NotesListViewModel::class.java
            )
    }

    private fun prepareRecyclerView() {
        notesListAdapter = NotesListAdapter().apply {
            setItemOnClickListener { note ->
                val editIntent = Intent(baseContext, NoteActivity::class.java).apply {
                    putExtra("id", note.id)
                    putExtra("title", note.title)
                    putExtra("description", note.description)
                    putExtra("priority", note.priority)
                }
                startActivityForResult(editIntent, request)
            }
        }
        notesList.layoutManager = LinearLayoutManager(this)
        notesList.adapter = notesListAdapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                notesListAdapter.currentList[viewHolder.adapterPosition]?.let {
                    notesListViewModel.delete(it)
                }
            }
        }).attachToRecyclerView(notesList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all -> {
                notesListViewModel.deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == request && resultCode == Activity.RESULT_OK) {
            data?.extras?.let {
                if (it.getInt("id", -1) != -1) {
                    notesListViewModel.update(Note(
                        title = it.getString("newTitle", ""),
                        description = it.getString("newDescription", ""),
                        priority = it.getInt("newPriority", -1)
                    ).apply {
                        id = it.getInt("id", -1)
                    })
                } else notesListViewModel.insert(
                    Note(
                        title = it.getString("newTitle", ""),
                        description = it.getString("newDescription", ""),
                        priority = it.getInt("newPriority", -1)
                    )
                )
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
