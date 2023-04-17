package com.example.englishttcm.playzone.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuizMode(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswerOne: String,
    val incorrectAnswerTwo: String,
    val incorrectAnswerThree: String,
)