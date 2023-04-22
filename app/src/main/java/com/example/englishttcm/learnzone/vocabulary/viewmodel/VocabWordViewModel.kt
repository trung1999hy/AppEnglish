package com.example.englishttcm.learnzone.vocabulary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord
import com.example.englishttcm.learnzone.vocabulary.repo.VocabularyRepository

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
}
