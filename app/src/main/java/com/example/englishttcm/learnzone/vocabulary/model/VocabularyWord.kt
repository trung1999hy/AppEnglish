package com.example.englishttcm.learnzone.vocabulary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VocabularyWord(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val word : String,
    val mean : String,
    val pronounce : String,
    val topicId : Int,
    val image : String,
)