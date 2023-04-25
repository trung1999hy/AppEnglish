package com.example.englishttcm.playzone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.playzone.model.Quiz
import com.example.englishttcm.playzone.repository.QuizRepository

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private var quizRepository: QuizRepository

    init {
        quizRepository = QuizRepository(application)
    }

    val currentQuiz: MutableLiveData<Quiz> = quizRepository.getCurrentQuiz

    fun shuffle(ans1: String, ans2: String, ans3: String, ans4: String): ArrayList<String>{
        return quizRepository.shuffleAns(ans1, ans2, ans3, ans4)
    }

    fun checkWin(): Boolean{
        return quizRepository.checkWin()
    }

    fun checkTrue(answered: String, trueAns: String): Boolean{
        return quizRepository.checkTrue(answered, trueAns)
    }

    fun next(){
        quizRepository.nextQuiz()
    }
}