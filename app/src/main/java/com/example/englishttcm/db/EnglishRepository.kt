package com.example.englishttcm.db

import androidx.lifecycle.LiveData
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.playzone.model.QuizMode

class EnglishRepository(private val englishDao: EnglishDao) {

    val readAllQuestion: LiveData<List<QuizMode>> = englishDao.readQuestion()
    val readAllVocabTopic: LiveData<List<VocabularyTopic>> = englishDao.readVocabTopic()
//    fun readVocabWordByTopicId(topicId : Int) = englishDao.readVocabWordByTopicId(topicId)
}