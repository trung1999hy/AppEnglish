package com.tpk.englishttcm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tpk.englishttcm.data.remote.firebase.model.Listening
import com.tpk.englishttcm.repo.ListeningRepository

class ListeningViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : ListeningRepository


    init {
        repository = ListeningRepository(application)

    }
    fun getAllListening(): MutableLiveData<List<Listening>> =
        repository.getAllListening()
}