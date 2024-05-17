package com.norm.aicameraattractions.di

import android.app.Application
import com.norm.aicameraattractions.data.repository.CameraRepositoryImpl
import com.norm.aicameraattractions.model.repository.CameraRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CameraRepositoryModule {
    @Provides
    @Singleton
    fun provideCameraRepository(
        application: Application,
    ): CameraRepository = CameraRepositoryImpl(application)
}