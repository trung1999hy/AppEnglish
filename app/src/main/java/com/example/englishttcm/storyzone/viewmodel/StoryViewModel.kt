package com.example.englishttcm.storyzone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.englishttcm.storyzone.model.Genre
import com.example.englishttcm.storyzone.model.Story
import com.example.englishttcm.storyzone.repo.StoryRepository

class StoryViewModel : ViewModel(){
    private var listGenreLive : LiveData<List<Genre>>
    private var repository : StoryRepository = StoryRepository()

    init {
        listGenreLive = repository.getListGenresLive()
    }

    val getListGenreLive : LiveData<List<Genre>>
    get() = listGenreLive
    fun getListStoryLive(genreId : String) : LiveData<List<Story>> = repository.getListStoryLive(genreId)

}