package com.nurbk.ps.noteapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nurbk.ps.noteapp.data.models.Note


@Dao
interface NoteDao {

    @Query("SELECT * FROM note  ORDER BY id DESC")
    fun getNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)



}