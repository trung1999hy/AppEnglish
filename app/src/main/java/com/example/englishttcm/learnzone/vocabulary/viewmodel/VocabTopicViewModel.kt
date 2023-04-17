package com.example.englishttcm.learnzone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishttcm.learnzone.model.VocabularyTopic

class VocabTopicViewModel : ViewModel(){
    private val listVocabTopicLive : MutableLiveData<ArrayList<VocabularyTopic>> = MutableLiveData()

    init {
        initData()
    }
    private fun initData() {
        val listVocabTopic = ArrayList<VocabularyTopic>()
        for (i in 1..20) {
            listVocabTopic.add(VocabularyTopic(i, "Topic $i", "Unknown"))
        }
        listVocabTopicLive.value = listVocabTopic
    }

    fun getListVocabTopic() : MutableLiveData<ArrayList<VocabularyTopic>> = listVocabTopicLive
}