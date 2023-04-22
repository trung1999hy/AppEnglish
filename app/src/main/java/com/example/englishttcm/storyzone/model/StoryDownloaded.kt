package com.example.englishttcm.storyzone.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Story")
data class StoryDownloaded(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    @ColumnInfo("nameStory")
    val name: String,
    @ColumnInfo("pathStory")
    val path: String,
    @ColumnInfo("currentPage")
    var currentPage: Int,
)
