package com.nurbk.ps.noteapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nurbk.ps.noteapp.data.models.ImagePath
import com.nurbk.ps.noteapp.data.models.Note

class Converters {

    @TypeConverter
    fun gsonToLinks(json: String?): ImagePath {
        return Gson().fromJson(json, ImagePath::class.java)
    }

    @TypeConverter
    fun factLinksToGson(images: ImagePath): String? {
        return Gson().toJson(images)
    }


}