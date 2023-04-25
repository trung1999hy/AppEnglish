package com.example.englishttcm.learnzone.listening.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.learnzone.listening.model.Listening
import com.example.englishttcm.learnzone.listening.repo.ListeningRepository
import com.example.englishttcm.translate.repo.TranslateRepository

class ListeningViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : ListeningRepository


    init {
        repository = ListeningRepository(application)

    }
    fun getAllListening(): MutableLiveData<List<Listening>> =
        repository.getAllListening()
}