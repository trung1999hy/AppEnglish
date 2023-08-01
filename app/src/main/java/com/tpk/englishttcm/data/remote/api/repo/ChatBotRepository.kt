package com.tpk.englishttcm.data.remote.api.repo

import com.tpk.englishttcm.callback.OnCallApiCompleteCallBack
import com.tpk.englishttcm.data.remote.api.service.ApiService
import com.tpk.englishttcm.data.remote.api.response.MessageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotRepository {

    fun getMessageFromBot(message: String, onCallApiListener: OnCallApiCompleteCallBack) {
        val apiService = ApiService.createService()
            .getMessage(ApiService.BID, ApiService.API_KEY, ApiService.UID, message)
        apiService.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                val resultBot = response.body()
                onCallApiListener.onSuccess(resultBot?.cnt)
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                onCallApiListener.onFailed(t.message)
            }
        })
    }
}