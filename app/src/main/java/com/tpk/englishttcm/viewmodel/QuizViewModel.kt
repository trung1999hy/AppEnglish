package com.tpk.englishttcm.viewmodel

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tpk.englishttcm.data.remote.firebase.model.Quiz
import com.tpk.englishttcm.repo.QuizRepository

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

    fun checkTrue(answered: String, trueAns: String): Boolean {
        return quizRepository.checkTrue(answered, trueAns)
    }

    fun next() {
        quizRepository.nextQuiz()
    }

    fun fiftyFiftySupport(tvTrueAnswer: TextView,tv1: TextView,tv2: TextView,tv3: TextView) {
        quizRepository.fiftyFiftySupport(tvTrueAnswer,tv1,tv2,tv3);
    }
}