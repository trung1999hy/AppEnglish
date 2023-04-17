package com.example.englishttcm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.englishttcm.playzone.model.QuizMode

@Dao
interface EnglishDao {

    @Query("select * from QuizMode")
    fun readQuestion(): LiveData<List<QuizMode>>
}