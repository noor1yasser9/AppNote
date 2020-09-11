package com.nurbk.ps.noteapp.repository

import com.nurbk.ps.noteapp.data.db.NoteDatabase
import com.nurbk.ps.noteapp.data.models.Note

class NoteRepository(
    val db: NoteDatabase
) {


    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)

    fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    fun getNotes() = db.getNoteDao().getNotes()

}