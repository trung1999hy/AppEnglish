package com.tpk.englishttcm.repo

import android.app.Application
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.tpk.englishttcm.data.remote.firebase.model.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class QuizRepository(_application: Application) {

    private var application: Application
    private var db: FirebaseFirestore
    private var listQuiz = arrayListOf<Quiz>()
    private var _currentQuiz = MutableLiveData<Quiz>()
    private var currentPos: Int = 0

    init {
        application = _application
        db = FirebaseFirestore.getInstance()
        getQuiz()
    }


    private fun getQuiz(){
        db.collection("quiz").get().addOnCompleteListener {
            if(it.isSuccessful && it.result != null){
                for(data in it.result){
                    val word = data.getString("word").toString()
                    val trueAnswer = data.getString("trueAnswer").toString()
                    val incorrectAnsOne = data.getString("incorrectAnsOne").toString()
                    val incorrectAnsTwo = data.getString("incorrectAnsTwo").toString()
                    val incorrectAnsThree = data.getString("incorrectAnsThree").toString()
                    val quiz = Quiz(word, trueAnswer, incorrectAnsOne, incorrectAnsTwo, incorrectAnsThree)
                    listQuiz.add(quiz)
                }
                listQuiz.shuffle()
                setCurrent()
            }
        }.addOnFailureListener{
            Toast.makeText(application, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun checkTrue(answered: String, trueAnswer: String): Boolean{
        return answered == trueAnswer
    }

    private fun setCurrent(){
        _currentQuiz.value = listQuiz[currentPos]
    }

    fun shuffleAns(ans1: String, ans2: String, ans3: String, ans4: String): ArrayList<String>{
        val listAns = arrayListOf(ans1, ans2, ans3, ans4)
        listAns.shuffle()
        return listAns
    }

    fun nextQuiz(){
        currentPos++
        _currentQuiz.value = listQuiz[currentPos]
    }

    fun checkWin(): Boolean {
        return currentPos >=3
    }


    val getCurrentQuiz: MutableLiveData<Quiz>
        get() = _currentQuiz

    fun fiftyFiftySupport(
        tvTrueAnswer: TextView,
        tv1: TextView,
        tv2: TextView,
        tv3: TextView
    ) {
        val rd = Random.nextInt(1, 4)
        if (tvTrueAnswer.text.toString() == listQuiz[currentPos].trueAnswer) {
            when (rd) {
                1 -> {
                    tv1.isEnabled = false
                    tv2.isEnabled = false
                    tv1.text = ""
                    tv2.text = ""
                }
                2 -> {
                    tv2.isEnabled = false
                    tv3.isEnabled = false
                    tv2.text = ""
                    tv3.text = ""
                }
                3 -> {
                    tv1.isEnabled = false
                    tv3.isEnabled = false
                    tv1.text = ""
                    tv3.text = ""
                }
            }
        }
    }
}

