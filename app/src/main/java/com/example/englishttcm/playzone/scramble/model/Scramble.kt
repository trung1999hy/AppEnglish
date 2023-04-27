package com.example.englishttcm.playzone.scramble.model


import androidx.room.Entity
import androidx.room.PrimaryKey

data class Scramble(
    val id: String? = null,
    val scrambled: String? = null,
    val original: String? = null
)