package com.example.englishttcm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord
import com.example.englishttcm.playzone.model.QuizMode
import com.example.englishttcm.storyzone.model.StoryDownloaded

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