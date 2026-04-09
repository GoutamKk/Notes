package com.example.notes.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.Database.NotesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository):ViewModel() {
    fun getAll(): LiveData<List<NotesData>>{
        return repository.getAll()
    }
    fun insertNote(notesData: NotesData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(notesData)
        }
    }
    fun deleteNote(notesData: NotesData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(notesData)
        }
    }
    fun updteNote(notesData: NotesData){
        viewModelScope.launch(Dispatchers.IO){
            repository.upadteNote(notesData)
        }
    }
    fun searchNote(query:String): LiveData<List<NotesData>>{
        return if (query.isEmpty()){
            getAll()
        }
        else{
            repository.searchNote(query)
        }
    }
    fun getOne(id:Int): LiveData<NotesData>{
        return repository.getOne(id)
    }
}