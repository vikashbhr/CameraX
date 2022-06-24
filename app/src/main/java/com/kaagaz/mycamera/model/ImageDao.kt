package com.kaagaz.mycamera.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(image:ImageModel)

    @Update(entity = ImageModel::class)
    suspend fun update(image:ImageModel)

    @Delete(entity = ImageModel::class)
    suspend fun delete(image: ImageModel)

    @Query("SELECT * FROM image_table")
    fun getImages():LiveData<List<ImageModel>>

}