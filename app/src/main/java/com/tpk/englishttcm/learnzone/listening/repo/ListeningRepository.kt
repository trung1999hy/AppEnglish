package com.tpk.englishttcm.learnzone.listening.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tpk.englishttcm.learnzone.listening.model.Listening
import com.google.firebase.firestore.FirebaseFirestore

class ListeningRepository(_application: Application) {
    private var application: Application
    private val db: FirebaseFirestore
    private lateinit var listening: Listening
    init {
        application = _application
        db = FirebaseFirestore.getInstance()
    }

    fun getAllListening():MutableLiveData<List<Listening>>{
        val listListening = MutableLiveData<List<Listening>>()
        db.collection("listening").get()
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful && task.result != null){
                    val list = ArrayList<Listening>()
                    for(document in task.result){
                        val image = document.getString("image")
                        val title = document.getString("title")
                        val content = document.getString("content")
                        val titleTranslate = document.getString("titleTranslate")
                        val contentTranslate = document.getString("contentTranslate")
                        val audio = document.getString("audio")
                        listening = Listening(image,title,content,titleTranslate,contentTranslate,audio)
                        list.add(listening)

                    }
                    listListening.value = list
                }
            }
            .addOnFailureListener {
            }
        return  listListening
    }
}