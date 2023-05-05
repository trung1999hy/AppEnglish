package com.example.englishttcm.learnzone.vocabulary.repo

import android.app.Application
import android.content.ContentValues
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.IOException
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class VocabularyRepository(_application: Application) {
    private var topicList : MutableLiveData<ArrayList<VocabularyTopic>>
    private var wordList : MutableLiveData<ArrayList<VocabularyWord>>
    private var mediaPlayer: MediaPlayer? = null
    private var application: Application

    private var db: FirebaseFirestore = Firebase.firestore
    private lateinit var topic: VocabularyTopic

    val getFirebaseVocabTopic: MutableLiveData<ArrayList<VocabularyTopic>>
        get() = topicList

    val getFirebaseVocabWord: MutableLiveData<ArrayList<VocabularyWord>>
        get() = wordList


    init {
        application = _application
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
                    topicList.postValue(list)
                }
            }
            .addOnFailureListener {
            }
    }

    fun vocabularyWord(idTopic: Int){
        val wordsRef = db.collection("vocabularyWord")
        wordsRef
            .whereEqualTo("topicId", idTopic)
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

    fun noteWord(vocabWord: VocabularyWord){
        val notebook: HashMap<String, Any> = HashMap()
        notebook["word"] = vocabWord.word.toString()
        notebook["mean"] = vocabWord.mean.toString()
        notebook["speaker"] = vocabWord.speaker.toString()
        notebook["pronounce"] = vocabWord.pronounce.toString()
        notebook["example"] = vocabWord.example.toString()
        db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("bookmark").add(notebook)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
}
