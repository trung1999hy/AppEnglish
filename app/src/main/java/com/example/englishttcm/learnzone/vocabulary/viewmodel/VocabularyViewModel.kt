package com.example.englishttcm.learnzone.vocabulary.viewmodel

import android.app.Application
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord
import com.example.englishttcm.learnzone.vocabulary.repo.VocabularyRepository
import java.util.*
import kotlin.collections.ArrayList

class VocabularyViewModel(application: Application): AndroidViewModel(application) {
    private var repository: VocabularyRepository
    lateinit var textToSpeech: TextToSpeech

    private var _vocabTopicList = MutableLiveData<ArrayList<VocabularyTopic>>()
    val vocabTopicList: LiveData<ArrayList<VocabularyTopic>> get() = _vocabTopicList

    private var _vocabTopicWord = MutableLiveData<ArrayList<VocabularyWord>>()
    val vocabTopicWord: LiveData<ArrayList<VocabularyWord>> get() = _vocabTopicWord

    init {
        repository = VocabularyRepository(application)
        _vocabTopicList = repository.getFirebaseVocabTopic
        _vocabTopicWord = repository.getFirebaseVocabWord
    }

    fun getVocabTopicList(){
        repository.vocabularyTopic()
    }

    fun getVocabWordList(topicId: Int){
        repository.vocabularyWord(topicId)
    }
    /*fun speakWord(context: Context): TextToSpeech{
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.isLanguageAvailable(Locale.UK)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("textToSpeech", "Language is not supported")
                } else {
                    textToSpeech.language = Locale.UK
                }
            } else {
                Log.e("textToSpeech", "Initialization failed")
            }
        }
        return textToSpeech

    }*/

    fun speakWord(audioUrl: String){
        repository.speak(audioUrl)
    }
}
