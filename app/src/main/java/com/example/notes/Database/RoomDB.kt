package com.example.notes.Database

import android.content.Context
import android.os.Build
import androidx.room.Database
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database( entities = [NotesData::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract val notesDao: NotesDao
    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
    companion object{
        private var notesDatabase : RoomDB?= null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("Alter Table NotesData ADD COLUMN body TEXT NOT NULL DEFAULT ''")
            }
        }
        fun getInstance(context: Context): RoomDB {
            if(notesDatabase == null){
                notesDatabase = Room.databaseBuilder(context, RoomDB::class.java,"NotesDatabase")
                    .allowMainThreadQueries()   //absence of this causes cannot access database on main thread error
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
            return notesDatabase!!
        }
    }
}