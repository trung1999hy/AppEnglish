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
import com.example.englishttcm.storyzone.util.Util
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream

class StoryRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference
    private var downloadTasks = mutableListOf<FileDownloadTask>()
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

    fun downloadFile(story: Story, context: Context, listener: OnDownloadCompleteListener) {
        val root = Util.getRoot(context)
        val folderFile = File(root, "books")
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
        tasks.forEach { task ->
            downloadTasks.add(task)
        }
        Tasks.whenAllSuccess<Any>(*tasks.toTypedArray())
            .addOnSuccessListener {
                val story = StoryDownloaded(story.id, story.name, story.url, 0)
                listener.onDownloadComplete(true)
                GlobalScope.launch {
                    try {
                        EnglishDatabase.getDatabase(context).getEnglishDao()
                            .insertStoryDownloaded(story)
                        Log.d("Long", "Insert success")
                    } catch (e: Exception) {
                        Log.d("Long", "Insert failed")
                    }

                }
                Log.d("Long", "Download success")
            }
            .addOnFailureListener {
                listener.onDownloadFailed(true)
                Log.d("Long", "Download failed")
            }
    }

    fun cancelDownload(story: Story, context: Context): LiveData<Boolean> {
        val checkSuccess = MutableLiveData<Boolean>()
        try {
            downloadTasks.forEach { task ->
                task.cancel()
            }
            downloadTasks.clear()
            val storyDownloaded = StoryDownloaded(story.id, story.name, story.url, 0)
            deleteFileFromLocal(storyDownloaded, context)
            checkSuccess.value = true
        } catch (e: java.lang.Exception) {
            checkSuccess.value = false
        }
        return checkSuccess
    }

    fun loadImageFromFirebaseStorage(fileName: String, listener: OnDownloadCompleteListener) {
        val imageRef = storage.child("stories_pdf/$fileName.png")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Log.d("Long", "Load image success")
            listener.onDownloadComplete(uri.toString())
        }.addOnFailureListener { exception ->
            exception.message?.let { listener.onDownloadFailed(it) }
            Log.d("Long", "Load image failed")
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

    fun getAllStoryDownloaded(context: Context): LiveData<List<StoryDownloaded>> {
        return EnglishDatabase.getDatabase(context).getEnglishDao().readAllStoryDownloaded()
    }

    fun getPdfFromLocal(fileName: String, context: Context): LiveData<File> {
        val fileLive = MutableLiveData<File>()
        val root = Util.getRoot(context)
        val file = File(root, "books/$fileName.pdf")
        fileLive.value = file
        return fileLive
    }

    fun deleteFileFromLocal(story: StoryDownloaded, context: Context) {
        GlobalScope.launch {
            val root = Util.getRoot(context)
            val filePdf = File(root, "books/${story.path}.pdf")
            val fileImage = File(root, "books/${story.path}.png")
            if (filePdf.exists()) {
                filePdf.delete()
            }
            if (fileImage.exists()) {
                fileImage.delete()
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            try {
                EnglishDatabase.getDatabase(context).getEnglishDao()
                    .deleteStoryDownloaded(story)
                Log.d("Long", "Delete success")
            } catch (e: Exception) {
                Log.d("Long", "Delete failed")
            }

        }
    }

    fun getStoryDownloadById(storyId: String, context: Context) =
        EnglishDatabase.getDatabase(context).getEnglishDao().readStoryDownloadedById(storyId)
}