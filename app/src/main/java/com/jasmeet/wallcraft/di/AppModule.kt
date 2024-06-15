package com.jasmeet.wallcraft.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.dao.PhotosDao
import com.jasmeet.wallcraft.model.database.AppDatabase
import com.jasmeet.wallcraft.model.repo.CategoriesRepo
import com.jasmeet.wallcraft.model.repo.CategoryDetailsRepo
import com.jasmeet.wallcraft.model.repo.DetailsRepo
import com.jasmeet.wallcraft.model.repo.DownloadRepo
import com.jasmeet.wallcraft.model.repo.FirebaseRepo
import com.jasmeet.wallcraft.model.repo.HomeRepo
import com.jasmeet.wallcraft.model.repo.PhotographerPhotosRepo
import com.jasmeet.wallcraft.model.repo.PostedByRepo
import com.jasmeet.wallcraft.model.repo.WallpaperRepo
import com.jasmeet.wallcraft.model.repoImpl.CategoriesRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.CategoryDetailsRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.DetailsRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.DownloadRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.FirebaseRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.HomeRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.PhotographerPhotosRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.PostedByRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.WallpaperRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesFirebaseDatabase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun providesUserRepository(auth: FirebaseAuth, db: FirebaseFirestore): FirebaseRepo {
        return FirebaseRepoImpl(auth, db)
    }

    @Provides
    @Singleton
    fun providesApiService(): ApiService {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun providesLocalDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "Photos_Database"
        ).build()
    }

    @Provides
    fun providesPostDao(database: AppDatabase): PhotosDao {
        return database.photoDao()
    }

    @Provides
    @Singleton
    fun providesHomeRepo(apiService: ApiService): HomeRepo =
        HomeRepoImpl(apiService)

    @Provides
    @Singleton
    fun providesDetailsRepo(apiService: ApiService): DetailsRepo =
        DetailsRepoImpl(apiService)

    @Provides
    @Singleton
    fun providesDownloadsRepo(context: Context): DownloadRepo =
        DownloadRepoImpl(context)

    @Provides
    @Singleton
    fun providesWallpapersRepo(context: Context): WallpaperRepo =
        WallpaperRepoImpl(context)

    @Provides
    @Singleton
    fun providesPostedByRepo(apiService: ApiService): PostedByRepo =
        PostedByRepoImpl(apiService)

    @Provides
    @Singleton
    fun providesPostedByPhotosRepo(apiService: ApiService): PhotographerPhotosRepo =
        PhotographerPhotosRepoImpl(apiService)

    @Provides
    @Singleton
    fun providesCategoriesRepo(apiService: ApiService): CategoriesRepo =
        CategoriesRepoImpl(apiService)

    @Provides
    @Singleton
    fun providesCategoryDetailsRepo(apiService: ApiService): CategoryDetailsRepo =
        CategoryDetailsRepoImpl(apiService)
}