package com.example.englishttcm.playzone.model

import android.app.Application
import androidx.cardview.widget.CardView
import androidx.lifecycle.AndroidViewModel
import com.example.englishttcm.db.EnglishDatabase
import com.example.englishttcm.db.EnglishRepository

class QuizViewModel (application: Application): AndroidViewModel(application){
    private val englishDao = EnglishDatabase.getDatabase(application).getEnglishDao()
    private val repository = QuizRepository(englishDao)
    val readAllQuestionVocab = repository.readAllQuestion
    var CorrectCount = 0

    fun Correct(cardView: CardView){
        
    }
}