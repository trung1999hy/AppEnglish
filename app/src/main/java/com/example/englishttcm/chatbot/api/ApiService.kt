package com.example.englishttcm.chatbot.api

import com.example.englishttcm.chatbot.model.ResultBot
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("get")
    fun getMessage(
        @Query("bid") bid: Int,
        @Query("key") key: String,
        @Query("uid") uid: String,
        @Query("msg") msg: String,
    ): Call<ResultBot>

    companion object {
        fun createService(): ApiService {
            val gson = GsonBuilder()
                .setDateFormat("yy-MM-dd")
                .create()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        private const val BASE_URL = "http://api.brainshop.ai/"
        const val API_KEY = "ZvNJrar39eEGZ1Sb"
        const val BID = 174827
        const val UID = "[uid]"
    }
}


