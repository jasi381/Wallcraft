package com.jasmeet.wallcraft.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.dao.DownloadsDao
import com.jasmeet.wallcraft.model.dao.FavouriteDao
import com.jasmeet.wallcraft.model.database.DownloadsDatabase
import com.jasmeet.wallcraft.model.database.FavouritesDatabase
import com.jasmeet.wallcraft.model.repo.CategoriesRepo
import com.jasmeet.wallcraft.model.repo.CategoryDetailsRepo
import com.jasmeet.wallcraft.model.repo.DetailsRepo
import com.jasmeet.wallcraft.model.repo.DownloadRepo
import com.jasmeet.wallcraft.model.repo.DownloadsDbRepo
import com.jasmeet.wallcraft.model.repo.FavouritesDbRepo
import com.jasmeet.wallcraft.model.repo.FirebaseRepo
import com.jasmeet.wallcraft.model.repo.HomeRepo
import com.jasmeet.wallcraft.model.repo.PhotographerPhotosRepo
import com.jasmeet.wallcraft.model.repo.PostedByRepo
import com.jasmeet.wallcraft.model.repo.RandomImageRepo
import com.jasmeet.wallcraft.model.repo.WallpaperRepo
import com.jasmeet.wallcraft.model.repoImpl.CategoriesRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.CategoryDetailsRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.DetailsRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.DownloadRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.DownloadsDbRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.FavouritesDbRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.FirebaseRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.HomeRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.PhotographerPhotosRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.PostedByRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.RandomImageRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.WallpaperRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    @Singleton
    fun providesFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun providesContentResolver(context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore,
        storage: FirebaseStorage,
        contentResolver: ContentResolver
    ): FirebaseRepo {
        return FirebaseRepoImpl(auth, db, storage, contentResolver)
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
    fun provideFavouriteDao(db: FavouritesDatabase): FavouriteDao {
        return db.favouriteDao()
    }

    @Provides
    @Singleton
    fun providesFavouritesDatabase(app: Application): FavouritesDatabase {
        return Room.databaseBuilder(app, FavouritesDatabase::class.java, "fav_Db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDownloadsDao(db: DownloadsDatabase): DownloadsDao {
        return db.downloadsDao()
    }

    @Provides
    @Singleton
    fun providesDownloadsDatabase(app: Application): DownloadsDatabase {
        return Room.databaseBuilder(app, DownloadsDatabase::class.java, "downloads_Db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun providesFavouritesDbRepository(favouriteDao: FavouriteDao): FavouritesDbRepo {
        return FavouritesDbRepoImpl(favouriteDao)
    }

    @Provides
    fun providesDownloadsDbRepository(downloadsDao: DownloadsDao): DownloadsDbRepo {
        return DownloadsDbRepoImpl(downloadsDao)
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


    @Provides
    @Singleton
    fun providesRandomImageRepo(apiService: ApiService): RandomImageRepo =
        RandomImageRepoImpl(apiService)
}