package com.create.fragments.di

import com.create.fragments.api.NotesApi
import com.create.fragments.api.UserApi
import com.create.fragments.repository.NotesRepository
import com.create.fragments.repository.UserRepository
import com.create.fragments.utils.Const.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {


    @Provides
    @Singleton
    fun getRetrofitInstance2(): UserApi {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val request: Request = chain.request().newBuilder().addHeader(
                "api-key",
                ""
            ).build()
            chain.proceed(request)

        }).addInterceptor(interceptor = logging).connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(UserApi::class.java)
    }




    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    }



    @Singleton
    @Provides
    fun providesUserRepo(api: UserApi):UserRepository{
        return UserRepository(api)
    }


    @Singleton
    @Provides
    fun providesNotesApi(retrofit: Retrofit):NotesApi{
        return retrofit.create(NotesApi::class.java)
    }
    @Singleton
    @Provides
    fun providesNotesRepo(api: NotesApi):NotesRepository{
        return NotesRepository(api)
    }

}