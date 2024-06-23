package com.alhalimthoriqfebrian.noteapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.alhalimthoriqfebrian.noteapp.R
import com.alhalimthoriqfebrian.noteapp.database.MyDatabase
import com.alhalimthoriqfebrian.noteapp.databinding.ActivityEditNoteBinding
import com.alhalimthoriqfebrian.noteapp.model.NoteModel
import java.util.Date
import java.util.concurrent.Executors

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var database: MyDatabase
    var idNote = 0
    var title = ""
    var description = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_note)
        idNote = intent.getIntExtra("id", 0)
        title = intent.getStringExtra("title") ?: ""
        description = intent.getStringExtra("description") ?: ""
        binding.activity = this
        database = MyDatabase.getDatabase(this)
    }
    private fun validateUsername(): Boolean {
        return if (binding.editTitleNote.text.toString().trim().isEmpty()) {
            binding.editTitleNoteTextInputLayout.error = getString(R.string.title_empty_error)
            binding.editTitleNote.requestFocus()
            false
        } else {
            binding.editTitleNoteTextInputLayout.isErrorEnabled = false
            true
        }
    }
    private fun validateDescription(): Boolean {
        return if (binding.editNoteDescription.text.toString().trim().isEmpty()) {
            binding.editNoteDescriptionTextInputLayout.error = getString(R.string.description_empty_error)
            binding.editNoteDescription.requestFocus()
            false
        } else {
            binding.editNoteDescriptionTextInputLayout.isErrorEnabled = false
            true
        }
    }
    fun editData(view: View?) {
        title = binding.editTitleNote.text.toString().trim()
        description = binding.editNoteDescription.text.toString().trim()
        val isUsernameValid = validateUsername()
        val isDescriptionValid = validateDescription()
        if (isUsernameValid && isDescriptionValid) {
            val noteData = NoteModel(title, description).apply {
                id = idNote
            }
            Executors.newSingleThreadExecutor().execute {
                database.noteDao().update(noteData)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            Toast.makeText(this, getString(R.string.data_successfully_updated), Toast.LENGTH_SHORT).show()
        }
    }
}
