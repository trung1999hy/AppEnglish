package com.example.englishttcm.storyzone.viewmodel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.englishttcm.storyzone.callback.OnDownloadCompleteListener
import com.example.englishttcm.storyzone.model.Genre
import com.example.englishttcm.storyzone.model.Story
import com.example.englishttcm.storyzone.model.StoryDownloaded
import com.example.englishttcm.storyzone.repo.StoryRepository

class StoryViewModel(application: Application) : AndroidViewModel(application) {
    private val _listGenreLive: LiveData<List<Genre>>
    private val repository = StoryRepository()
    private val _listStoryDownloadedLive: LiveData<List<StoryDownloaded>>

    init {
        _listGenreLive = repository.getListGenresLive()
        _listStoryDownloadedLive = repository.getAllStoryDownloaded(application)
    }

    val getListGenreLive: LiveData<List<Genre>>
        get() = _listGenreLive
    val getListStoryDownloadedLive: LiveData<List<StoryDownloaded>>
        get() = _listStoryDownloadedLive

    fun getListStoryLive(genreId: String): LiveData<List<Story>> =
        repository.getListStoryLive(genreId)

    fun checkPermission(activity: Activity, story: Story, context: Context) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            downloadFile(story, context)
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    fun downloadFile(story: Story, context: Context) =
        repository.downloadFile(story, context)

    fun loadImageFromFirebase(fileName: String, listener: OnDownloadCompleteListener) {
        repository.loadImageFromFirebaseStorage(fileName, object : OnDownloadCompleteListener {
            override fun onDownloadComplete(downloadUrl: String) {
                listener.onDownloadComplete(downloadUrl)
                Log.d("Long", downloadUrl)
            }

            override fun onDownloadFailed(errorMessage: String?) {
                Log.d("Long", "load failed")
            }
        })
    }

    fun loadImageFromLocal(fileName: String, context: Context) =
        repository.loadImageFromLocal(fileName, context)


    fun checkIsDownloadStory(storyId: String, context: Context) =
        repository.getStoryDownloadById(storyId, context)

    companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 1
    }
}