package com.example.englishttcm.chatbot.repo

import com.example.englishttcm.chatbot.OnCallApiCompleteListener
import com.example.englishttcm.chatbot.api.ApiService
import com.example.englishttcm.chatbot.model.ResultBot
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotRepository {

    fun getMessageFromBot(message: String, onCallApiListener: OnCallApiCompleteListener) {
        val apiService = ApiService.createService()
            .getMessage(ApiService.BID, ApiService.API_KEY, ApiService.UID, message)
        apiService.enqueue(object : Callback<ResultBot> {
            override fun onResponse(
                call: Call<ResultBot>,
                response: Response<ResultBot>
            ) {
                val resultBot = response.body()
                onCallApiListener.onSuccess(resultBot?.cnt)
            }

            override fun onFailure(call: Call<ResultBot>, t: Throwable) {
                onCallApiListener.onFailed(t.message)
            }
        })
    }
}