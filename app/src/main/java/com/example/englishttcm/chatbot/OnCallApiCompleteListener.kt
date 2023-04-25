package com.example.englishttcm.chatbot

interface OnCallApiCompleteListener {
    fun onSuccess(data: Any?)
    fun onFailed(data: Any?)
}