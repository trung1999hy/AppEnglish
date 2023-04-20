package com.example.englishttcm.playzone.scramble.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.playzone.scramble.model.ScrambleModel
import com.google.firebase.firestore.FirebaseFirestore

class ScrambleRepository {
    val db = FirebaseFirestore.getInstance()
    fun getWords(): LiveData<List<ScrambleModel>> {
        val data = MutableLiveData<List<ScrambleModel>>()
        val usersRef = db.collection("vocabulary_word")
        usersRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val scrambleList = mutableListOf<ScrambleModel>()
                for (document in task.result!!) {
                    val id = document.id
                    val word = document.getString("word")
                    val vocab = ScrambleModel(id, word!!)
                    scrambleList.add(vocab)
                }
                data.value = scrambleList
            } else {
                Log.e("Error", "getScrambleError")
            }
        }
        return data
    }
}