package com.tpk.englishttcm.repo

import com.tpk.englishttcm.data.local.EnglishDao

class EnglishRepository(private val englishDao: EnglishDao) {

    val readAllStoryDownloaded = englishDao.readAllStoryDownloaded()
//    fun readStoryDownloadedById(storyId : Int) = englishDao.readStoryDownloadedById(storyId)
}