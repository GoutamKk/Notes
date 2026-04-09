package com.example.notes.Model

import androidx.lifecycle.LiveData
import com.example.notes.Database.NotesDao
import com.example.notes.Database.NotesData

class Repository(private val notesDao : NotesDao ) {

    fun getAll(): LiveData<List<NotesData>>{
        return notesDao.getAll() as LiveData<List<NotesData>>
    }

    suspend fun insertNote(note: NotesData){
        notesDao.insertNote(note)
    }

    suspend fun upadteNote(note: NotesData){
        notesDao.updateNote(note)
    }

    suspend fun deleteNote(note: NotesData){
        notesDao.deleteNote(note)
    }

    fun getOne(id:Int): LiveData<NotesData>{
        return notesDao.getData(id)
    }

    fun searchNote(query: String): LiveData<List<NotesData>>{
        return notesDao.searchNote("%$query")
    }
}