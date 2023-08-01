package com.tpk.englishttcm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tpk.englishttcm.data.local.entity.StoryDownloaded

@Database(entities = [StoryDownloaded::class], version = 1, exportSchema = false)
abstract class EnglishDatabase : RoomDatabase() {
    abstract fun getEnglishDao(): EnglishDao

    companion object {
        @Volatile
        private var INSTANCE: EnglishDatabase? = null

        fun getDatabase(context: Context): EnglishDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EnglishDatabase::class.java,
                    "englishDB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}