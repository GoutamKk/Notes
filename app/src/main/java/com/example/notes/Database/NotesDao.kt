package com.example.notes.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(notesData: NotesData)

    @Query("Select * from NotesData Order by time DESC")
    fun getAll(): LiveData<List<NotesData>>

    @Update
    fun updateNote(notesData: NotesData):Int

    @Delete
    fun deleteNote(notesData: NotesData):Int

    @Query("SELECT * FROM NotesData WHERE title LIKE '%' || :query || '%' OR text LIKE '%' || :query || '%'")
    fun searchNote(query: String): LiveData<List<NotesData>>

    @Query("Select * From NotesData where id = :id")
    fun getData(id:Int): LiveData<NotesData>

}