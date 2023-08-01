package com.tpk.englishttcm.callback

interface OnDownloadCompleteListener {
    fun onDownloadComplete(data: Any?)
    fun onDownloadFailed(data: Any?)
}