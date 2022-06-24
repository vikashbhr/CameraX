package com.kaagaz.mycamera.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album_table")
data class AlbumModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val albumName: String?,
    val date:String?,
    val photos: List<ImageModel>?
)
