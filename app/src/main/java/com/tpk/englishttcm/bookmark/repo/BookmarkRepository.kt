package com.tpk.englishttcm.bookmark.repo

import android.app.Application
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tpk.englishttcm.bookmark.model.BookmarkWord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.IOException

@Suppress("DEPRECATION")
class BookmarkRepository(_application: Application) {
    private var wordList = MutableLiveData<ArrayList<BookmarkWord>>()
    private var mediaPlayer: MediaPlayer? = null
    private var application: Application

    private var db: FirebaseFirestore = Firebase.firestore

    val getFirebaseWordList: MutableLiveData<ArrayList<BookmarkWord>>
        get() = wordList

    init {
        application = _application
        wordList = MutableLiveData()
    }

    fun bookmarkWord(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid).collection("bookmark")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        val list = ArrayList<BookmarkWord>()
                        for (document in task.result!!) {
                            val id = document.id
                            val word = document.getString("word")
                            val mean = document.getString("mean")
                            val speaker = document.getString("speaker")
                            val pronounce = document.getString("pronounce")
                            val example = document.getString("example")
                            val notebookWord = BookmarkWord(
                                id,
                                word,
                                mean,
                                speaker,
                                pronounce,
                                example
                            )
                            list.add(notebookWord)

                        }
                        wordList.postValue(list)
                    }
                }
                .addOnFailureListener {
                }
        }
    }

    fun speak(audioUrl: String){
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try{
            mediaPlayer!!.setDataSource(audioUrl)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        }catch (e : IOException){
            e.printStackTrace()
        }
    }

    fun deleteNote(id: String){
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid).collection("bookmark").document(id)
                .delete()
                .addOnCompleteListener {
                    Log.d("DeleteNote", "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener {
                }
        }
    }

    fun updateWord(bookmarkWord: BookmarkWord) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid).collection("bookmark").document(bookmarkWord.id.toString())
                .set(bookmarkWord)
                .addOnSuccessListener {
                    Log.d("Edit", "Bookmark word updated successfully!")
                }
                .addOnFailureListener {
                    Log.e("Edit", "Error updating notebook word", it)
                }
        }
    }
}