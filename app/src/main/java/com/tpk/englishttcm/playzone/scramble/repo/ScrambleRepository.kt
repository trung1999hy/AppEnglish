package com.tpk.englishttcm.playzone.scramble.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tpk.englishttcm.playzone.scramble.model.Scramble
import com.google.firebase.firestore.FirebaseFirestore

class ScrambleRepository {

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
                words.shuffle()
                data.value = words.take(10)
            } else {
                Log.e("Error", "getScrambleError")
            }
        }
        return data
    }
}