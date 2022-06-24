package com.kaagaz.mycamera.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class ImageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imageName:String?,
    val imageUrl:String?
)
