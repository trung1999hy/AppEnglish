package com.example.englishttcm.chatbot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishttcm.chatbot.OnCallApiCompleteListener
import com.example.englishttcm.chatbot.model.Message
import com.example.englishttcm.chatbot.repo.ChatBotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatBotViewModel : ViewModel() {
    private val _listMessageLive = MutableLiveData<List<Message>>()
    val listMessageLive: LiveData<List<Message>>
        get() = _listMessageLive
    private val repository: ChatBotRepository = ChatBotRepository()

    fun addMessage(message: Message) {
        viewModelScope.launch {
            val updateList = withContext(Dispatchers.Default) {
                val currentList = _listMessageLive.value ?: emptyList()
                val newList = currentList + message
                newList
            }
            withContext(Dispatchers.Main) {
                _listMessageLive.value = updateList
            }
        }
    }

    fun sendMessageToBot(message: String, onCallApi: OnCallApiCompleteListener) {
        repository.getMessageFromBot(
            message,
            object : OnCallApiCompleteListener {
                override fun onSuccess(data: Any?) {
                    onCallApi.onSuccess(data)
                }

                override fun onFailed(data: Any?) {
                    onCallApi.onFailed(data)
                }
            })
    }
}
