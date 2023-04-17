package com.example.englishttcm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.englishttcm.playzone.model.QuizMode

@Database(entities = [QuizMode::class], version = 1)
abstract class EnglishDatabase : RoomDatabase() {
    abstract fun getEnglishDao(): EnglishDao

    companion object {
        @Volatile
        private var INSTANCE: EnglishDatabase? = null

        fun getDatabase(context: Context): EnglishDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EnglishDatabase::class.java,
                    "englishDB.db"
                ).createFromAsset("db/englishDB.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}