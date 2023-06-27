package com.tpk.englishttcm.learnzone.listening.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tpk.englishttcm.learnzone.listening.model.Listening
import com.tpk.englishttcm.learnzone.listening.repo.ListeningRepository

class ListeningViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : ListeningRepository


    init {
        repository = ListeningRepository(application)

    }
    fun getAllListening(): MutableLiveData<List<Listening>> =
        repository.getAllListening()
}