package com.tpk.englishttcm.learnzone.listening.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tpk.englishttcm.learnzone.listening.service.MyService

class Receiver:BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        val action = p1!!.getIntExtra("action_listening",0)
        val intent = Intent(p0,MyService::class.java)
        intent.putExtra("action_listening_service",action)

        p0!!.startService(intent)
    }
}