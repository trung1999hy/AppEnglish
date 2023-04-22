package com.example.englishttcm.learnzone.vocabulary.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class VocabularyRepository(_application: Application) {
    private var topicList : MutableLiveData<ArrayList<VocabularyTopic>>
    private var wordList : MutableLiveData<ArrayList<VocabularyWord>>

    private var db = Firebase.firestore
    private lateinit var topic: VocabularyTopic

    val getFirebaseVocabTopic: MutableLiveData<ArrayList<VocabularyTopic>>
        get() = topicList

    val getFirebaseVocabWord: MutableLiveData<ArrayList<VocabularyWord>>
        get() = wordList


    init {
        topicList = MutableLiveData<ArrayList<VocabularyTopic>>()
        wordList = MutableLiveData<ArrayList<VocabularyWord>>()
    }
    fun vocabularyTopic(){
        db = FirebaseFirestore.getInstance()

        db.collection("vocabularyTopic").get()
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful && task.result != null){
                    val list = ArrayList<VocabularyTopic>()
                    for(document in task.result){
                        val topicId = document.id.toInt()
                        val name = document.getString("name")
                        val image = document.getString("image")
                        topic = VocabularyTopic(topicId,name,image)
                        list.add(topic)

                    }
                    Log.d("List", "${list}")
                    topicList.postValue(list)
                }
            }
            .addOnFailureListener {
            }
    }

    fun vocabularyWord(topicid: Int){
        val wordsRef = db.collection("vocabularyWord")
        wordsRef
            .whereEqualTo("topicId", topicid)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val list = ArrayList<VocabularyWord>()
                    for (document in task.result!!) {
                        val id = document.id
                        val word = document.getString("word")
                        val mean = document.getString("mean")
                        val speaker = document.getString("speaker")
                        val pronounce = document.getString("pronounce")
                        val topicId = document.getLong("topicId")!!.toInt()
                        val image = document.getString("image")
                        val example = document.getString("example")
                        val vocabWord = VocabularyWord(
                            id,
                            word,
                            mean,
                            speaker,
                            pronounce,
                            topicId,
                            image,
                            example
                        )
                        list.add(vocabWord)

                    }
                    wordList.postValue(list)
                }
            }
    }

}
