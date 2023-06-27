package com.tpk.englishttcm.storyzone.viewmodel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.tpk.englishttcm.storyzone.callback.OnDownloadCompleteListener
import com.tpk.englishttcm.storyzone.model.Genre
import com.tpk.englishttcm.storyzone.model.Story
import com.tpk.englishttcm.storyzone.model.StoryDownloaded
import com.tpk.englishttcm.storyzone.repo.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoryViewModel(application: Application) : AndroidViewModel(application) {
    private val _listGenreLive: MutableLiveData<List<Genre>>
    val getListGenreLive: LiveData<List<Genre>>
        get() = _listGenreLive
    private val _listStoryDownloadedLive: LiveData<List<StoryDownloaded>>
    val getListStoryDownloadedLive: LiveData<List<StoryDownloaded>>
        get() = _listStoryDownloadedLive
    private val _isPermissionGranted = MutableLiveData<Boolean>()
    val isPermissionGranted: LiveData<Boolean>
        get() = _isPermissionGranted
    private val _insertResult = MutableLiveData<Boolean>()
    val insertResult: LiveData<Boolean>
        get() = _insertResult
    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean>
        get() = _deleteResult
    private val _deleteLocalResult = MutableLiveData<Boolean>()
    val deleteLocalResult: LiveData<Boolean>
        get() = _deleteLocalResult
    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean>
        get() = _updateResult
    private val _cancelResult = MutableLiveData<Boolean>()
    val cancelResult: LiveData<Boolean>
        get() = _cancelResult
    private val repository = StoryRepository()

    init {
        _listGenreLive = repository.getListGenresLive()
        _listStoryDownloadedLive = repository.getAllStoryDownloaded(application)
    }

    fun getListStoryLive(genreId: String): LiveData<List<Story>> =
        repository.getListStoryLive(genreId)

    fun checkPermission(activity: Activity) {
        _isPermissionGranted.value =
            (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
                    == PackageManager.PERMISSION_GRANTED)
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
            }

            override fun onDownloadFailed(data: Any?) {
            }
        })
    }

    fun loadImageFromLocal(fileName: String, context: Context): LiveData<Bitmap> =
        repository.loadImageFromLocal(fileName, context)

    fun getPdfFromLocal(fileName: String, context: Context) =
        repository.getPdfFromLocal(fileName, context)

    fun getStoryDownloadById(storyId: String, context: Context) =
        repository.getStoryDownloadById(storyId, context)

    fun cancelDownload() {
        viewModelScope.launch {
            repository.cancelDownload()
            withContext(Dispatchers.Main) {
                _cancelResult.value = true
            }
        }
    }

    fun updateStoryDownload(story: StoryDownloaded, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateStoryDownload(story, context)
            }
            withContext(Dispatchers.Main) {
                _updateResult.value = true
            }
        }
    }

    fun deleteFileLocal(story: StoryDownloaded, context: Context) {
        viewModelScope.launch {
            repository.deleteFileLocal(story, context)
            withContext(Dispatchers.Main) {
                _deleteLocalResult.value = true
            }
        }
    }

    fun insertStoryDownload(story: StoryDownloaded, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertStoryDownload(story, context)
            }
            withContext(Dispatchers.Main) {
                _insertResult.value = true
            }
        }
    }

    fun deleteStoryDownloaded(story: StoryDownloaded, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteStoryDownload(story, context)
            }
            withContext(Dispatchers.Main) {
                _deleteResult.value = true
            }
        }
    }
}