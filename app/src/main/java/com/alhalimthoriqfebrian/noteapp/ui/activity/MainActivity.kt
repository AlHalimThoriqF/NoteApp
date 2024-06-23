package com.alhalimthoriqfebrian.noteapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.alhalimthoriqfebrian.noteapp.R
import com.alhalimthoriqfebrian.noteapp.adapter.NoteAdapter
import com.alhalimthoriqfebrian.noteapp.database.MyDatabase
import com.alhalimthoriqfebrian.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: MyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        database = MyDatabase.getDatabase(this)
        database.noteDao().getAll().observe(this) {
            binding.adapter = NoteAdapter(it) {
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("id", it.id)
                    putExtra("title", it.title)
                    putExtra("description", it.description)
                }
                startActivity(intent)
            }}}
    fun switchDarkMode(isChecked: Boolean) {
        if (binding.switchDarkMode.isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }}
    fun addData(view: View) {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }}