package com.example.englishttcm.learnzone.listening.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import com.example.englishttcm.R
import com.example.englishttcm.learnzone.listening.model.Listening
import com.example.englishttcm.learnzone.listening.receiver.Receiver

class MyService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private val binder = LocalBinder()
    private var isPlaying:Boolean = false
    private var isRepeat:Boolean = false
    private var isSlowMotion:Boolean = false
    private lateinit var mListening:Listening
    companion object {
        const val ACTION_PAUSE: Int = 1
        const val ACTION_RESUME: Int = 2
        const val ACTION_CLEAR: Int = 3
    }


    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): MyService = this@MyService
    }


    override fun onBind(intent: Intent): IBinder {
        return binder

    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle = intent?.extras
        val listening = bundle?.get("listening") as? Listening
        if(listening!=null){
            mListening = listening

            startListening(listening)
            sendNotification(listening)
        }

        val actionListening = intent?.getIntExtra("action_listening_service",0)
        if(actionListening != null){
            handleActionListening(actionListening)


        }


        return START_NOT_STICKY
    }

    private fun handleActionListening(actionListening: Int) {
        when (actionListening){
            ACTION_PAUSE -> {
                pauseListening()
                isPlaying = false
            }
            ACTION_RESUME -> resumeListening()
            ACTION_CLEAR ->{
                stopForeground(true)
                stopSelf()
                if(mediaPlayer != null){
                    mediaPlayer!!.release()
                    mediaPlayer = null
                }
            }
        }

    }

    private fun startListening(listening: Listening) {
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setDataSource(applicationContext, Uri.parse(listening.audio))
            mediaPlayer!!.prepare()
        }
        mediaPlayer!!.start()
        isPlaying = true
    }


   fun pauseListening(){
        if(mediaPlayer != null && isPlaying){
            mediaPlayer!!.pause()
            isPlaying = false
            sendNotification(mListening)
            isPlaying()

        }
    }
   fun resumeListening(){
        if(mediaPlayer != null && !isPlaying){
            mediaPlayer!!.start()
            isPlaying = true
            sendNotification(mListening)
            isPlaying()

        }

   }
    fun seekTo(i: Int) {
        mediaPlayer!!.seekTo(i)
    }
    fun replay5s(){
        val currentPosition = mediaPlayer?.currentPosition

        if (currentPosition != null && currentPosition >= 5000) {
            mediaPlayer?.seekTo(currentPosition - 5000)
        } else {
            mediaPlayer?.seekTo(0)
        }
    }
    fun forward5s(){
        val currentPosition = mediaPlayer?.currentPosition ?: 0
        val newPosition = currentPosition + 5000
        mediaPlayer?.seekTo(newPosition)
    }
    fun repeat(){
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.start()
        }
        isRepeat = true


    }
    fun noRepeat(){
        mediaPlayer?.setOnCompletionListener(null)
        isRepeat = false
    }

    fun slowMotion(){
        val playbackParams = PlaybackParams().setSpeed(0.75f)
        mediaPlayer?.setPlaybackParams(playbackParams)
        isSlowMotion = true
    }
    fun normalSpeed(){
        val playbackParams = PlaybackParams().setSpeed(1f)
        mediaPlayer?.setPlaybackParams(playbackParams)
        isSlowMotion = false
    }







    private fun sendNotification(listening: Listening) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.app_icon)
        val mediaSessionCompat = MediaSessionCompat(this,"tag")



        val builder = NotificationCompat.Builder(this,"MYCHANNEL")
            .setSmallIcon(R.drawable.ic_headphone)
            .setContentTitle(listening.title)
            .setLargeIcon(bitmap)
            .setStyle(
                MediaStyle()
                    .setMediaSession(mediaSessionCompat.sessionToken)
                    .setShowActionsInCompactView(0,1)
            )
        if(isPlaying){
            builder
                .addAction(R.drawable.ic_pause,"Pause",getPendingIntent(this, ACTION_PAUSE))
                .addAction(R.drawable.ic_close,"Close", getPendingIntent(this, ACTION_CLEAR))
        }
        if(!isPlaying){
            builder
                .addAction(R.drawable.ic_play,"Play",getPendingIntent(this, ACTION_RESUME))
                .addAction(R.drawable.ic_close,"Close", getPendingIntent(this, ACTION_CLEAR))
        }

        val notification = builder.build()

        startForeground(1,notification)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        if(mediaPlayer != null){
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }
    private fun getPendingIntent(context: Context, action:Int ):PendingIntent {
        val i = Intent(context, Receiver::class.java)
        i.putExtra("action_listening",action)
        return PendingIntent.getBroadcast(context,action,i,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
    fun getListening() :Listening = mListening
    fun isPlaying() :Boolean = isPlaying
    fun getCurrentPosition() = mediaPlayer?.currentPosition
    fun getDuration() = mediaPlayer?.duration
    fun isRepeat():Boolean = isRepeat
    fun isSlowMotion():Boolean = isSlowMotion



}