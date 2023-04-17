package com.example.englishttcm.db

import androidx.lifecycle.LiveData
import com.example.englishttcm.playzone.model.QuizMode

class EnglishRepository(private val englishDao: EnglishDao) {

    val readAllQuestion: LiveData<List<QuizMode>> = englishDao.readQuestion()

}