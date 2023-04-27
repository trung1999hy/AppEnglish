package com.example.englishttcm.playzone.scramble.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.playzone.scramble.model.Scramble
import com.google.firebase.auth.FirebaseAuth
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
                data.value = words
            } else {
                Log.e("Error", "getScrambleError")
            }
        }
        return data
    }

    fun updateCoin() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("users").document(userId).get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val coin = documentSnapshot.getLong("coin")?.toInt() ?: 0
                db.collection("users").document(userId).update("coin", coin + 20)
            }
        }
    }
}