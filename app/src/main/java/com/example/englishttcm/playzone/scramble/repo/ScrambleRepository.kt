package com.example.englishttcm.playzone.scramble.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.playzone.scramble.model.Scramble
import com.google.firebase.firestore.FirebaseFirestore

class ScrambleRepository {

    private var _currentWordCount: Int = 0
    private var _score: Int = 0

    private val db = FirebaseFirestore.getInstance()
    fun getWords(): LiveData<List<Scramble>> {
        val data = MutableLiveData<List<Scramble>>()
        val usersRef = db.collection("scrambles")
        usersRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val words = mutableListOf<Scramble>()
                for (document in task.result!!) {
                    val id = document.id
                    val scrambled = document.getString("scrambled").toString()
                    val original = document.getString("original").toString()
                    val word = Scramble(id, scrambled, original)
                    words.add(word)
                }
                data.value = words
            } else {
                Log.e("Error", "getScrambleError")
            }
        }
        return data
    }
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        getWords()
    }
    private fun increaseScore() {
        _score += 10
    }
    fun checkTrue(answered: String, trueAns: String): Boolean {
        if (answered == trueAns ) {
            increaseScore()
            return true
        }
        return false
    }
    fun nextWord(): Boolean {
       if (_currentWordCount < 10) {
           getWords()
           return true
       } else {
           return false
       }
    }


}