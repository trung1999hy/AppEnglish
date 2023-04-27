package com.example.englishttcm.learnzone.vocabulary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VocabularyWord(
    @PrimaryKey(autoGenerate = true)
    val id: String? = null,
    val word: String? = null,
    val mean: String? = null,
    val speaker: String? = null,
    val pronounce: String? = null,
    val topicId: Int? = null,
    val image: String? = null,
    val example: String? = null
)