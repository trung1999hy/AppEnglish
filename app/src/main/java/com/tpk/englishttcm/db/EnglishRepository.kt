package com.tpk.englishttcm.db

class EnglishRepository(private val englishDao: EnglishDao) {

    val readAllStoryDownloaded = englishDao.readAllStoryDownloaded()
//    fun readStoryDownloadedById(storyId : Int) = englishDao.readStoryDownloadedById(storyId)
}