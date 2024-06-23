package com.alhalimthoriqfebrian.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alhalimthoriqfebrian.noteapp.model.NoteDao
import com.alhalimthoriqfebrian.noteapp.model.NoteModel

@Database(
    entities = [NoteModel::class],
    version = 3, // Versi database
    exportSchema = false // exportSchema digunakan untuk mengekspor skema database, misalnya ke file JSON
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null
        fun getDatabase(context: Context): MyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "noteData_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }}}}

