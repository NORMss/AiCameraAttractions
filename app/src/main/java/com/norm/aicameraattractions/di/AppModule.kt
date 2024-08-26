package com.norm.aicameraattractions.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.norm.aicameraattractions.constants.OPENAI_API_KEY
import com.norm.aicameraattractions.data.local.LandmarkDao
import com.norm.aicameraattractions.data.local.LandmarkDatabase
import com.norm.aicameraattractions.data.local.UriTypeConverter
import com.norm.aicameraattractions.data.remote.AndroidDownloader
import com.norm.aicameraattractions.data.remote.OpenAiApi
import com.norm.aicameraattractions.data.repository.CameraRepositoryImpl
import com.norm.aicameraattractions.data.repository.LandmarkRepositoryImpl
import com.norm.aicameraattractions.data.repository.OpenAiRepositoryImlp
import com.norm.aicameraattractions.domain.remote.Downloader
import com.norm.aicameraattractions.domain.repository.CameraRepository
import com.norm.aicameraattractions.domain.repository.LandmarkRepository
import com.norm.aicameraattractions.domain.repository.OpenAiRepository
import com.norm.aicameraattractions.domain.usecases.camerausecases.CameraUseCases
import com.norm.aicameraattractions.domain.usecases.camerausecases.SavePhoto
import com.norm.aicameraattractions.domain.usecases.camerausecases.TakePhoto
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.DeleteLandmark
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.LandmarkUseCases
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.SelectLandmark
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.SelectLandmarks
import com.norm.aicameraattractions.domain.usecases.landmarkusecases.UpsertLandmark
import com.norm.aicameraattractions.domain.usecases.openaiusecases.GetChatCompletion
import com.norm.aicameraattractions.domain.usecases.openaiusecases.OpenAiUseCases
import com.norm.aicameraattractions.util.LANDMARKS_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
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
    fun provideOpenAiRepository(
        openAiApi: OpenAiApi,
    ): OpenAiRepository = OpenAiRepositoryImlp(openAiApi)

    @Provides
    @Singleton
    fun provideOpenAiUseCases(
        openAiRepository: OpenAiRepository,
    ): OpenAiUseCases {
        return OpenAiUseCases(
            getChatCompletion = GetChatCompletion(openAiRepository)
        )
    }

    @Provides
    @Singleton
    fun provideDownloader(
        @ApplicationContext context: Context,
    ): Downloader {
        return AndroidDownloader(context)
    }

    @Provides
    @Singleton
    fun provideOpenAiApi(): OpenAiApi {
        val client = OkHttpClient.Builder().apply {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            addInterceptor(logging)
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $OPENAI_API_KEY")
                    .build()
                chain.proceed(request)
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl(OpenAiApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}