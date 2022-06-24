package com.kaagaz.mycamera.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kaagaz.mycamera.model.AlbumModel
import com.kaagaz.mycamera.model.ImageModel
import com.kaagaz.mycamera.model.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ImageRepository): ViewModel() {

    val albumList: LiveData<List<AlbumModel>>? = repository.getAlbums()


}