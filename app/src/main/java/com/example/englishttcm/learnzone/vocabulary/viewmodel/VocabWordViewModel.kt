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
            listVocabWord.add(VocabularyWord(i, "physical $i", "vật lí $i", "&physical", i +1, "https://media.istockphoto.com/id/1319785900/vector/sketch-of-physics-lab-with-working-little-people.jpg?s=1024x1024&w=is&k=20&c=3C3Olq4f43o4saoKGBdqFAPJ1B_GCcZzLbWLosooVQo="))
        }
        listVocabWordLive.value = listVocabWord
    }

    fun getListVocabTopic() : MutableLiveData<List<VocabularyWord>> = listVocabWordLive
}