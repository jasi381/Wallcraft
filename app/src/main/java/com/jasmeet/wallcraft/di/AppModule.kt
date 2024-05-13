package com.jasmeet.wallcraft.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.dao.PhotosDao
import com.jasmeet.wallcraft.model.database.AppDatabase
import com.jasmeet.wallcraft.model.repo.DetailsRepo
import com.jasmeet.wallcraft.model.repo.HomeRepo
import com.jasmeet.wallcraft.model.repo.LoginSignUpRepo
import com.jasmeet.wallcraft.model.repoImpl.DetailsRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.HomeRepoImpl
import com.jasmeet.wallcraft.model.repoImpl.LoginSignUpRepoImpl
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

    @Provides
    @Singleton
    fun providesFirebaseDatabase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun providesUserRepository(auth: FirebaseAuth, db: FirebaseFirestore): LoginSignUpRepo {
        return LoginSignUpRepoImpl(auth, db)
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
}