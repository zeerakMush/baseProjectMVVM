package com.zk.base_project.di

import android.content.Context
import androidx.room.Room
import com.zk.base_project.database.AppDatabase
import com.zk.base_project.database.doa.DummyDoa
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, AppDatabase.DATA_BASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun getDummyDoa(db: AppDatabase): DummyDoa {
        return db.dummyDoa()
    }

}