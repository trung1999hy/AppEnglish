package com.tpk.englishttcm.application

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.tpk.englishttcm.until.PreferenceManager

private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294"
private const val LOG_TAG = "MyApplication"

class MyApplication : Application(), LifecycleObserver {
    private var currentActivity: Activity? = null
    private var isFirstCoin = false
    private lateinit var pref: PreferenceManager
    companion object {
        private lateinit var instance: MyApplication

        @JvmStatic
        fun getInstance(): MyApplication {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        pref = PreferenceManager.getInstance(this)
        pref.putBoolean("isFirstCoin", true)
        isFirstCoin = pref.getBoolean("isFirstCoin")
        if (isFirstCoin && pref.getValueCoin() == 0) {
            pref.setValueCoin(2)
            pref.putBoolean("isFirstCoin", false)
        }
        createNotificationChannel()
        //
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "MYCHANNEL",
                "my channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setSound(null, null)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    fun getPreference(): PreferenceManager {
        return pref
    }






}