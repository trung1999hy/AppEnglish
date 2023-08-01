package com.tpk.englishttcm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpk.englishttcm.callback.OnCallApiCompleteCallBack
import com.tpk.englishttcm.data.remote.api.response.Message
import com.tpk.englishttcm.data.remote.api.repo.ChatBotRepository
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

    fun sendMessageToBot(message: String, onCallApi: OnCallApiCompleteCallBack) {
        repository.getMessageFromBot(
            message,
            object : OnCallApiCompleteCallBack {
                override fun onSuccess(data: Any?) {
                    onCallApi.onSuccess(data)
                }

                override fun onFailed(data: Any?) {
                    onCallApi.onFailed(data)
                }
            })
    }
}
