package com.tpk.englishttcm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tpk.englishttcm.data.remote.firebase.model.VocabularyTopic
import com.tpk.englishttcm.data.remote.firebase.model.VocabularyWord
import com.tpk.englishttcm.repo.VocabularyRepository
import kotlin.collections.ArrayList

class VocabularyViewModel(application: Application): AndroidViewModel(application) {
    private var repository: VocabularyRepository

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

    fun speakWord(audioUrl: String){
        repository.speak(audioUrl)
    }

    fun getNoteWord(word: VocabularyWord){
        repository.noteWord(word)
    }
}
