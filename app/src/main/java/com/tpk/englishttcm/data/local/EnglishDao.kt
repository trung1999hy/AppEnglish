package com.tpk.englishttcm.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tpk.englishttcm.data.local.entity.StoryDownloaded

@Dao
interface EnglishDao {

    @Query("select * from Story")
    fun readAllStoryDownloaded(): LiveData<List<StoryDownloaded>>

    @Query("SELECT * FROM Story WHERE id = :storyId")
    fun readStoryDownloadedById(storyId: String): LiveData<StoryDownloaded>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStoryDownloaded(story: StoryDownloaded)
    @Delete
    suspend fun deleteStoryDownloaded(story: StoryDownloaded)
    @Update
    suspend fun updateStoryDownloaded(story: StoryDownloaded)
}