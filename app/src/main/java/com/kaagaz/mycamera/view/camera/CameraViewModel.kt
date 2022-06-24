package com.kaagaz.mycamera.view.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaagaz.mycamera.model.AlbumModel
import com.kaagaz.mycamera.model.ImageDao
import com.kaagaz.mycamera.model.ImageModel
import com.kaagaz.mycamera.model.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    val imageList: LiveData<List<ImageModel>>? = repository.getImages()
    val tempMutableImageList: MutableLiveData<ArrayList<ImageModel>> = MutableLiveData()
    val tempImageList: ArrayList<ImageModel> = ArrayList()

    fun saveImage(imageModel: ImageModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertImage(imageModel)

            tempImageList.add(imageModel)
            tempMutableImageList.postValue(tempImageList)
        }
    }

    fun saveAlbum(albumModel: AlbumModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAlbum(albumModel)
        }
    }


}