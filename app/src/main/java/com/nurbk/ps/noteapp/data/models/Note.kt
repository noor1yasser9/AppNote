package com.nurbk.ps.noteapp.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
class Note(
    val title: String?,
    val dateTime: String?,
    val subtitle: String?,
    val noteText: String?,
    val imagePath: ImagePath?,
    val color: String?,
    val webLink: String?
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("imagePath"),
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(dateTime)
        parcel.writeString(subtitle)
        parcel.writeString(noteText)
        parcel.writeString(color)
        parcel.writeString(webLink)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }


}