package com.example.englishttcm.learnzone.vocabulary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord

class VocabWordViewModel : ViewModel(){
    private val listVocabWordLive : MutableLiveData<List<VocabularyWord>> = MutableLiveData()

    init {
        initData()
    }
    private fun initData() {
        val listVocabWord = ArrayList<VocabularyWord>()
        for (i in 1..20) {
        }
        listVocabWordLive.value = listVocabWord
    }

    fun getListVocabTopic() : MutableLiveData<List<VocabularyWord>> = listVocabWordLive
}