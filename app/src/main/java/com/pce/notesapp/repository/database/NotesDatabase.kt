package com.pce.notesapp.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pce.notesapp.model.Note
import com.pce.notesapp.repository.dao.NotesDao

@Database(entities = [Note::class], version = 3)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var instance: NotesDatabase? = null
        private const val DATABASE_NAME = "notes_database"
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                buildDatabase(
                    context
                )
                    .also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, NotesDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

}