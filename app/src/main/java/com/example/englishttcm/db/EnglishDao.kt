package com.example.englishttcm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.playzone.model.QuizMode

@Dao
interface EnglishDao {

    @Query("select * from QuizMode")
    fun readQuestion(): LiveData<List<QuizMode>>

    @Query("select * from VocabularyTopic")
    fun readVocabTopic() : LiveData<List<VocabularyTopic>>

//    @Query("select * from VocabularyWord where topicId = :topicId")
//    fun readVocabWordByTopicId(topicId : Int) : LiveData<List<VocabularyWord>>
}