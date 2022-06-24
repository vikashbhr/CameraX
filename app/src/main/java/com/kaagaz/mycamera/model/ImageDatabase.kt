package com.kaagaz.mycamera.model

import android.content.Context
import androidx.room.Database
import com.kaagaz.mycamera.model.ImageModel
import androidx.room.RoomDatabase
import com.kaagaz.mycamera.model.ImageDao
import com.kaagaz.mycamera.model.ImageDatabase
import kotlin.jvm.Synchronized
import androidx.room.Room
import androidx.room.TypeConverters

@Database(entities = [ImageModel::class, AlbumModel::class], version = 1, exportSchema = false)
@TypeConverters(Convertor::class)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun albumDao(): AlbumDao

    companion object {
        private var INSTANCE: ImageDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ImageDatabase? {
            if (INSTANCE == null) INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ImageDatabase::class.java,
                "image_database"
            )
                .fallbackToDestructiveMigration()
                .build()
            return INSTANCE
        }
    }
}