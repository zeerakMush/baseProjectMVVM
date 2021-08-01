package com.zk.base_project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zk.base_project.BuildConfig
import com.zk.base_project.data.DummyItem
import com.zk.base_project.database.doa.DummyDoa

@Database(
    entities = [DummyItem::class],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATA_BASE_NAME = "${BuildConfig.APPLICATION_ID}_DB"
    }

    abstract fun dummyDoa(): DummyDoa
}