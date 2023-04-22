package com.example.englishttcm.storyzone.viewmodel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
    private val _isPermissionGranted = MutableLiveData<Boolean>()

    init {
        _listGenreLive = repository.getListGenresLive()
        _listStoryDownloadedLive = repository.getAllStoryDownloaded(application)
    }

    val getListGenreLive: LiveData<List<Genre>>
        get() = _listGenreLive
    val getListStoryDownloadedLive: LiveData<List<StoryDownloaded>>
        get() = _listStoryDownloadedLive
    val isPermissionGranted: LiveData<Boolean>
        get() = _isPermissionGranted

    fun getListStoryLive(genreId: String): LiveData<List<Story>> =
        repository.getListStoryLive(genreId)

    fun checkPermission(activity: Activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            _isPermissionGranted.value = true
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

    fun updatePermission(value: Boolean) {
        _isPermissionGranted.value = value
    }

    fun downloadFile(story: Story, context: Context, listener: OnDownloadCompleteListener) =
        repository.downloadFile(story, context, object : OnDownloadCompleteListener {
            override fun onDownloadComplete(data: Any?) {
                listener.onDownloadComplete(data)
            }

            override fun onDownloadFailed(data: Any?) {
                listener.onDownloadFailed(data)
            }

        })

    fun loadImageFromFirebase(fileName: String, listener: OnDownloadCompleteListener) {
        repository.loadImageFromFirebaseStorage(fileName, object : OnDownloadCompleteListener {
            override fun onDownloadComplete(data: Any?) {
                listener.onDownloadComplete(data)
                Log.d("Long", "load success")
            }

            override fun onDownloadFailed(data: Any?) {
                Log.d("Long", "load failed")
            }
        })
    }

    fun loadImageFromLocal(fileName: String, context: Context): LiveData<Bitmap> =
        repository.loadImageFromLocal(fileName, context)

    fun getPdfFromLocal(fileName: String, context: Context) =
        repository.getPdfFromLocal(fileName, context)

    fun getStoryDownloadById(storyId: String, context: Context) =
        repository.getStoryDownloadById(storyId, context)

    fun cancelDownload(story: Story, context: Context) = repository.cancelDownload(story, context)
    fun deleteFileFromLocal(story: StoryDownloaded, context: Context): LiveData<Boolean> {
        val checkIsDelete = MutableLiveData<Boolean>()
        try {
            repository.deleteFileFromLocal(story, context)
            checkIsDelete.value = true
        } catch (e: java.lang.Exception) {
            checkIsDelete.value = false
        }
        return checkIsDelete
    }

    companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 1
    }
}