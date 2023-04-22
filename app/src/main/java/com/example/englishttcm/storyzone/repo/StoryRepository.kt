package com.example.englishttcm.storyzone.repo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.db.EnglishDatabase
import com.example.englishttcm.storyzone.callback.OnDownloadCompleteListener
import com.example.englishttcm.storyzone.model.Genre
import com.example.englishttcm.storyzone.model.Story
import com.example.englishttcm.storyzone.model.StoryDownloaded
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream

class StoryRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference
    fun getListGenresLive(): MutableLiveData<List<Genre>> {
        val listGenreLive = MutableLiveData<List<Genre>>()
        fireStore.collection("genre").get().addOnSuccessListener { documents ->
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

    fun getListStoryLive(genreId: String): LiveData<List<Story>> {
        val listStoryLive = MutableLiveData<List<Story>>()
        fireStore.collection("story").whereEqualTo("genreid", genreId)
            .get().addOnSuccessListener { documents ->
                val listStory = mutableListOf<Story>()
                for (document in documents) {
                    val id = document.id
                    val author = document.getString("author") ?: ""
                    val describe = document.getString("describe") ?: ""
                    val name = document.getString("name") ?: ""
                    val url = document.getString("url") ?: ""
                    val story = Story(id, author, describe, name, url)
                    listStory += story
                }
                listStoryLive.value = listStory
            }
        return listStoryLive
    }

    fun downloadFile(story: Story, context: Context) {
        val downloadsFolder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        } else {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        }
        val folderFile = File(downloadsFolder, "books")
        if (!folderFile.exists()) {
            folderFile.mkdirs()
        }
        val bookFile = File(folderFile, "${story.url}.pdf")
        val imageFile = File(folderFile, "${story.url}.png")

        val storageRef = storage.child("stories_pdf/${story.url}.pdf")
        val imageRef = storage.child("stories_pdf/${story.url}.png")

        val tasks = arrayListOf(
            storageRef.getFile(bookFile),
            imageRef.getFile(imageFile)
        )

        Tasks.whenAllSuccess<Any>(*tasks.toTypedArray())
            .addOnSuccessListener {
                val story = StoryDownloaded(story.id, story.name, story.url, 0)
                GlobalScope.launch {
                    try {
                        EnglishDatabase.getDatabase(context).getEnglishDao().insertStoryDownloaded(story)
                        Log.d("Long", "Insert success")
                    } catch (e: Exception){
                        Log.d("Long", "Insert failed $e")
                    }

                }
                Log.d("Long", "Download success")
            }
            .addOnFailureListener {
                Log.d("Long", "Download failed")
            }
    }

    fun loadImageFromFirebaseStorage(fileName: String, listener: OnDownloadCompleteListener) {
        val imageRef = storage.child("stories_pdf/$fileName.png")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Log.d("Long", "Download success")
            listener.onDownloadComplete(uri.toString())
        }.addOnFailureListener { exception ->
            listener.onDownloadFailed(exception.message)
            Log.d("Long", "Download failed")
        }
    }

    fun loadImageFromLocal(fileName: String, context: Context): LiveData<Bitmap> {
        val root = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        } else {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        }
        val imagePath = File(root, "books/$fileName.png")
        val inputStream = FileInputStream(imagePath)
        val bitmapLive = MutableLiveData<Bitmap>()
        bitmapLive.value = BitmapFactory.decodeStream(inputStream)
        return bitmapLive
    }

    fun getAllStoryDownloaded(context: Context):LiveData<List<StoryDownloaded>>{
        return EnglishDatabase.getDatabase(context).getEnglishDao().readAllStoryDownloaded()
    }

    fun getStoryDownloadById(storyId: String, context: Context) = EnglishDatabase.getDatabase(context).getEnglishDao().readStoryDownloadedById(storyId)
}