package com.zk.base_project.di

import android.content.Context
import com.zk.base_project.network.URL
import com.zk.base_project.network.interceptor.AuthInterceptor
import com.zk.base_project.network.services.*
import com.zk.base_project.utils.PreferencesHelper
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .build()
    }

    @Provides
    fun provideMoshiConverter(): MoshiConverterFactory {
        return MoshiConverterFactory.create().withNullSerialization()
    }

    @Provides
    fun provideChuckInterceptor(@ApplicationContext context: Context): ChuckInterceptor {
        return ChuckInterceptor(context)
    }

    @Provides
    fun provideAuthInterceptor(@ApplicationContext context: Context, pref: PreferencesHelper): AuthInterceptor {
        return AuthInterceptor(pref)
    }

    @Provides
    fun provideDummyServices(retrofit: Retrofit): DummyApiServices {
        return retrofit.create(DummyApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
            authInterceptor: AuthInterceptor,
            chuckInterceptor: ChuckInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(chuckInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)//TODO: remove timeOut later
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })//TODO: remove it later
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: MoshiConverterFactory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(URL.URL_DEVELOPMENT)
                .addConverterFactory(moshi)
                .client(client)
                .build()
    }


}