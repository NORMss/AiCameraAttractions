package com.norm.aicameraattractions.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.norm.aicameraattractions.data.local.LandmarkDao
import com.norm.aicameraattractions.data.local.LandmarkDatabase
import com.norm.aicameraattractions.data.local.UriTypeConverter
import com.norm.aicameraattractions.data.remote.AndroidDownloader
import com.norm.aicameraattractions.data.repository.CameraRepositoryImpl
import com.norm.aicameraattractions.data.repository.LandmarkRepositoryImpl
import com.norm.aicameraattractions.domain.remote.Downloader
import com.norm.aicameraattractions.domain.repository.CameraRepository
import com.norm.aicameraattractions.domain.repository.LandmarkRepository
import com.norm.aicameraattractions.domain.usecases.camerausecases.CameraUseCases
import com.norm.aicameraattractions.domain.usecases.camerausecases.SavePhoto
import com.norm.aicameraattractions.domain.usecases.camerausecases.TakePhoto
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.DeleteLandmark
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.LandmarkUseCases
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.SelectLandmark
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.SelectLandmarks
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.UpsertLandmark
import com.norm.aicameraattractions.util.LANDMARKS_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        ).addTypeConverter(UriTypeConverter())
            .fallbackToDestructiveMigration(true)
            .build()
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

    @Provides
    @Singleton
    fun provideDownloader(
        @ApplicationContext context: Context,
    ): Downloader {
        return AndroidDownloader(context)
    }
}