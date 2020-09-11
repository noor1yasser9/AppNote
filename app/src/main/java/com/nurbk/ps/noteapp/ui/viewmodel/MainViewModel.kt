package com.nurbk.ps.noteapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.noteapp.data.db.NoteDatabase
import com.nurbk.ps.noteapp.data.models.Note
import com.nurbk.ps.noteapp.repository.NoteRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "MainViewModel"

    private val noteRepository =
        NoteRepository(NoteDatabase.invoke(application.applicationContext))


    fun insertNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun getNotes() = noteRepository.getNotes()

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

}