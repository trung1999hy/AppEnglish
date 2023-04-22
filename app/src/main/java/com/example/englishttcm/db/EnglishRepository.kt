package com.example.englishttcm.db

import androidx.lifecycle.LiveData
import com.example.englishttcm.storyzone.model.StoryDownloaded

class EnglishRepository(private val englishDao: EnglishDao) {

    val readAllStoryDownloaded = englishDao.readAllStoryDownloaded()
//    fun readStoryDownloadedById(storyId : Int) = englishDao.readStoryDownloadedById(storyId)
}