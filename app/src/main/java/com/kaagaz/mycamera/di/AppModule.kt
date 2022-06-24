package com.kaagaz.mycamera.di

import android.app.Application
import com.kaagaz.mycamera.model.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageRepository(application: Application)=  ImageRepository(application)
}