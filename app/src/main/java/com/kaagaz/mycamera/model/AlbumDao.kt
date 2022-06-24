package com.kaagaz.mycamera.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(albumModel: AlbumModel)

    @Update(entity = AlbumModel::class)
    suspend fun update(albumModel: AlbumModel)

    @Delete(entity = AlbumModel::class)
    suspend fun delete(albumModel: AlbumModel)

    @Query("SELECT * FROM album_table")
    fun getAlbums(): LiveData<List<AlbumModel>>

    @Query("SELECT * FROM album_table WHERE id=:id")
    fun getAlbum(id: Int): LiveData<AlbumModel>

}