package com.tpk.englishttcm.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class EnglishViewModel(application: Application): AndroidViewModel(application) {

    private val englishDao = EnglishDatabase.getDatabase(application).getEnglishDao()
    private val repository = EnglishRepository(englishDao)
    val readAllStoryDownloaded = repository.readAllStoryDownloaded
//    fun readAllStoryDownloaded(storyId:Int) = repository.readStoryDownloadedById(storyId)
}