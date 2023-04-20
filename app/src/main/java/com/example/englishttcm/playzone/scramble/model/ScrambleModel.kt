package com.example.englishttcm.playzone.scramble.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScrambleModel(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val word : String,
)