package com.example.englishttcm.db

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel

class EnglishViewModel(application: Application): AndroidViewModel(application) {


    private val englishDao = EnglishDatabase.getDatabase(application).getEnglishDao()
    private val repository = EnglishRepository(englishDao)
    val readAllQuestionVocab = repository.readAllQuestion
    private lateinit var englishViewModel: EnglishViewModel


}