package com.kaagaz.mycamera.view.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kaagaz.mycamera.model.AlbumModel
import com.kaagaz.mycamera.model.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {
    var albumId: Int? = null

    fun getAlbum(id:Int):LiveData<AlbumModel>? = repository.getAlbum(id)

}