package com.tpk.englishttcm.data.remote.firebase.model



data class Quiz(
    val word: String,
    val trueAnswer: String,
    val incorrectAnsOne: String,
    val incorrectAnsTwo: String,
    val incorrectAnsThree: String
)