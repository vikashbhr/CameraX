package com.kaagaz.mycamera.model

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class ImageRepository(application: Application) {

    private val imageDao = ImageDatabase.getInstance(application)?.imageDao()
    private val albumDao = ImageDatabase.getInstance(application)?.albumDao()


    suspend fun insertImage(imageModel: ImageModel) {
        imageDao?.insert(imageModel)
    }

    suspend fun updateImage(imageModel: ImageModel) {
        imageDao?.update(imageModel)
    }

    suspend fun deleteImage(imageModel: ImageModel) {
        imageDao?.delete(imageModel)
    }

    fun getImages(): LiveData<List<ImageModel>>? = imageDao?.getImages()


    suspend fun insertAlbum(albumModel: AlbumModel) {
        albumDao?.insert(albumModel)
    }

    suspend fun updateAlbum(albumModel: AlbumModel) {
        albumDao?.update(albumModel)
    }

    suspend fun deleteAlbum(albumModel: AlbumModel) {
        albumDao?.delete(albumModel)
    }

    fun getAlbums(): LiveData<List<AlbumModel>>? = albumDao?.getAlbums()

    fun getAlbum(id: Int): LiveData<AlbumModel>? = albumDao?.getAlbum(id)
}