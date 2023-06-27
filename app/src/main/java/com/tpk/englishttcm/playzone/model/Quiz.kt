package com.tpk.englishttcm.playzone.model



data class Quiz(
    val word: String,
    val trueAnswer: String,
    val incorrectAnsOne: String,
    val incorrectAnsTwo: String,
    val incorrectAnsThree: String
)