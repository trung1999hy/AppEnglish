package com.example.englishttcm.storyzone.callback

interface OnDownloadCompleteListener {
    fun onDownloadComplete(downloadUrl: String)
    fun onDownloadFailed(errorMessage: String?)
}