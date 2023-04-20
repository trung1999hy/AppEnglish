package com.example.englishttcm.storyzone.repo

import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.storyzone.model.Genre
import com.example.englishttcm.storyzone.model.Story
import com.google.firebase.firestore.FirebaseFirestore

class StoryRepository {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getListGenresLive(): MutableLiveData<List<Genre>> {
        val listGenreLive = MutableLiveData<List<Genre>>()
        db.collection("genre").get().addOnSuccessListener { documents ->
            val listGenre = mutableListOf<Genre>()
            for (document in documents) {
                val id = document.id
                val name = document.getString("name")
                if (name != null) {
                    val genre = Genre(id, name)
                    listGenre.add(genre)
                }
            }
            listGenreLive.value = listGenre
        }
        return listGenreLive
    }

    fun getListStoryLive(genreId: String): MutableLiveData<List<Story>> {
        val listStoryLive = MutableLiveData<List<Story>>()
        db.collection("story").whereEqualTo("genreid", genreId)
            .get().addOnSuccessListener { documents ->
                val listStory = mutableListOf<Story>()
                for (document in documents) {
                    val id = document.id
                    val author = document.getString("author")?:""
                    val describe = document.getString("describe")?:""
                    val image = document.getString("image")?:""
                    val name = document.getString("name")?:""
                    val url = document.getString("url")?:""
                    val story = Story(id, author, describe, image, name, url)
                    listStory += story
                }
                listStoryLive.value = listStory
            }
        return listStoryLive
    }
}