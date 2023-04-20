package com.example.englishttcm.learnzone.vocabulary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class VocabularyTopic(
    @PrimaryKey(autoGenerate = true)
    val topicId: Int,
    val name : String,
    val image : String,
)