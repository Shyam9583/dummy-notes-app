package com.pce.notesapp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.pce.notesapp.R
import com.pce.notesapp.ktx.toast

class NoteActivity : AppCompatActivity() {

    private lateinit var editTitle: EditText
    private lateinit var editDescription: EditText
    private lateinit var editPriority: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        bindView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_menu, menu)
        return true
    }

    private fun bindView() {

        editTitle = findViewById(R.id.editTitle)
        editDescription = findViewById(R.id.editDescription)
        editPriority = findViewById(R.id.editPriority)

        editPriority.minValue = 1
        editPriority.maxValue = 10

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear_black_24dp)
        intent.extras?.let {
            editTitle.setText(it.getString("title", ""))
            editDescription.setText(it.getString("description", ""))
            editPriority.value = it.getInt("priority", 1)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.saveButton -> {
                val data = Intent().apply {
                    val title = editTitle.text.toString().trim()
                    val description = editDescription.text.toString().trim()
                    if (title.isBlank() || description.isBlank()) {
                        toast("Neither can title nor description be blank")
                        return true
                    }
                    putExtra("newTitle", title)
                    putExtra("newDescription", description)
                    putExtra("newPriority", editPriority.value)
                }
                intent.extras?.let {
                    data.putExtra("id", it.getInt("id", -1))
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
