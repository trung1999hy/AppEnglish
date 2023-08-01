package com.tpk.englishttcm.data.remote.firebase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkWord (
    @PrimaryKey(autoGenerate = true)
    val id: String? = null,
    val word: String? = null,
    val mean: String? = null,
    val speaker: String? = null,
    val pronounce: String? = null,
    val example: String? = null
)
