package com.tpk.englishttcm.chatbot

interface OnCallApiCompleteListener {
    fun onSuccess(data: Any?)
    fun onFailed(data: Any?)
}