package com.tpk.englishttcm.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.tpk.englishttcm.callback.MyServiceCallback
import com.tpk.englishttcm.data.remote.firebase.model.Listening
import com.tpk.englishttcm.recevier.ListeningReceiver
import com.tpk.englishttcm.R

class ListeningService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private val binder = LocalBinder()
    private var isPlaying:Boolean = false
    private var isRepeat:Boolean = false
    private var isSlowMotion:Boolean = false
    private lateinit var mListening: Listening
    private var callback: MyServiceCallback? = null
    companion object {
        const val ACTION_PAUSE: Int = 1
        const val ACTION_RESUME: Int = 2
        const val ACTION_CLEAR: Int = 3
    }
    fun setCallback(callback: MyServiceCallback) {
        this.callback = callback
    }
    inner class LocalBinder : Binder() {
        fun getService(): ListeningService = this@ListeningService
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


        return START_REDELIVER_INTENT
    }

    private fun handleActionListening(actionListening: Int) {
        when (actionListening){
            ACTION_PAUSE -> {
                pauseListening()
            }
            ACTION_RESUME -> resumeListening()
            ACTION_CLEAR ->{
                stopForeground(STOP_FOREGROUND_REMOVE)
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
        callback?.onPlayingStateChanged(true)


    }




    fun pauseListening(){
        if(mediaPlayer != null && isPlaying){
            mediaPlayer!!.pause()
            isPlaying = false
            sendNotification(mListening)
            callback?.onPlayingStateChanged(isPlaying)


        }
    }
   fun resumeListening(){
        if(mediaPlayer != null && !isPlaying){
            mediaPlayer!!.start()
            isPlaying = true
            sendNotification(mListening)
            callback?.onPlayingStateChanged(isPlaying)
        }

   }
    fun seekTo(i: Int) {
        mediaPlayer?.seekTo(i)
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
        if(isPlaying){
            val playbackParams = PlaybackParams().setSpeed(0.75f)
            mediaPlayer?.playbackParams = playbackParams
            isSlowMotion = true
        }
        else {
            return
        }

    }
    fun normalSpeed(){
        val playbackParams = PlaybackParams().setSpeed(1f)
        mediaPlayer?.playbackParams = playbackParams
        isSlowMotion = false
    }

    private fun sendNotification(listening: Listening) {
        val mediaSessionCompat = MediaSessionCompat(this,"tag")



        val builder = NotificationCompat.Builder(this,"MYCHANNEL")
            .setSmallIcon(R.drawable.ic_headphone)
            .setContentTitle(listening.title)
            .setStyle(
                MediaStyle()
                    .setMediaSession(mediaSessionCompat.sessionToken)
                    .setShowActionsInCompactView(0,1)
            )


        Glide.with(this)
            .asBitmap()
            .load(listening.image)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    builder.setLargeIcon(resource)
                    if(isPlaying){
                        builder
                            .addAction(R.drawable.ic_pause,"Pause",getPendingIntent(this@ListeningService, ACTION_PAUSE))
                            .addAction(R.drawable.ic_close,"Close", getPendingIntent(this@ListeningService, ACTION_CLEAR))
                    }
                    if(!isPlaying){
                        builder
                            .addAction(R.drawable.ic_play,"Play",getPendingIntent(this@ListeningService, ACTION_RESUME))
                            .addAction(R.drawable.ic_close,"Close", getPendingIntent(this@ListeningService, ACTION_CLEAR))
                    }

                    val notification = builder.build()

                    startForeground(1,notification)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })


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
        val i = Intent(context, ListeningReceiver::class.java)
        i.putExtra("action_listening",action)
        return PendingIntent.getBroadcast(context,action,i,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
    fun isPlaying() :Boolean = isPlaying

    fun getCurrentPosition() = mediaPlayer?.currentPosition
    fun getDuration() = mediaPlayer?.duration
    fun isRepeat():Boolean = isRepeat
    fun isSlowMotion():Boolean = isSlowMotion



}