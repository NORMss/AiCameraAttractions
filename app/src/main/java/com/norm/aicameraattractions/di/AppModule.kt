package com.norm.aicameraattractions.di

import android.app.Application
import androidx.room.Room
import com.norm.aicameraattractions.data.local.LandmarkDao
import com.norm.aicameraattractions.data.local.LandmarkDatabase
import com.norm.aicameraattractions.data.repository.CameraRepositoryImpl
import com.norm.aicameraattractions.data.repository.LandmarkRepositoryImpl
import com.norm.aicameraattractions.model.repository.CameraRepository
import com.norm.aicameraattractions.model.repository.LandmarkRepository
import com.norm.aicameraattractions.model.usecases.camerausecases.CameraUseCases
import com.norm.aicameraattractions.model.usecases.camerausecases.SavePhoto
import com.norm.aicameraattractions.model.usecases.camerausecases.TakePhoto
import com.norm.aicameraattractions.model.usecases.landmarkusecases.DeleteLandmark
import com.norm.aicameraattractions.model.usecases.landmarkusecases.LandmarkUseCases
import com.norm.aicameraattractions.model.usecases.landmarkusecases.SelectLandmark
import com.norm.aicameraattractions.model.usecases.landmarkusecases.SelectLandmarks
import com.norm.aicameraattractions.model.usecases.landmarkusecases.UpsertLandmark
import com.norm.aicameraattractions.util.LANDMARKS_DB_NAME
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
    fun provideCameraRepository(
        application: Application,
    ): CameraRepository = CameraRepositoryImpl(application)

    @Provides
    @Singleton
    fun provideLandmarkDao(
        landmarkDatabase: LandmarkDatabase,
    ): LandmarkDao = landmarkDatabase.landmarkDao

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application,
    ): LandmarkDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = LandmarkDatabase::class.java,
            name = LANDMARKS_DB_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideLandmarkRepository(
        landmarkDao: LandmarkDao,
    ): LandmarkRepository = LandmarkRepositoryImpl(landmarkDao)

    @Provides
    @Singleton
    fun provideLandmarkUseCases(
        landmarkRepository: LandmarkRepository,
    ): LandmarkUseCases {
        return LandmarkUseCases(
            upsertLandmark = UpsertLandmark(landmarkRepository),
            deleteLandmark = DeleteLandmark(landmarkRepository),
            selectLandmarks = SelectLandmarks(landmarkRepository),
            selectLandmark = SelectLandmark(landmarkRepository),
        )
    }

    @Provides
    @Singleton
    fun provideCameraUseCases(
        cameraRepository: CameraRepository,
    ): CameraUseCases {
        return CameraUseCases(
            takePhoto = TakePhoto(cameraRepository),
            savePhoto = SavePhoto(cameraRepository),
        )
    }
}