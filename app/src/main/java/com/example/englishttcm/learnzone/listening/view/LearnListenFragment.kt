package com.example.englishttcm.learnzone.listening.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.englishttcm.MyServiceCallback
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLearnListeningBinding
import com.example.englishttcm.learnzone.listening.model.Listening
import com.example.englishttcm.learnzone.listening.service.MyService
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LearnListenFragment : BaseFragment<FragmentLearnListeningBinding>(), MyServiceCallback {
    private var mService: MyService? = null
    private var isServiceConnected: Boolean = false
    private lateinit var listening: Listening

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MyService.LocalBinder
            mService = binder.getService()
            isServiceConnected = true
            handleLayoutListening()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isServiceConnected = false
        }
    }

    override fun getLayout(container: ViewGroup?): FragmentLearnListeningBinding =
        FragmentLearnListeningBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        listening = data as Listening
        initLayout()
        binding.btnPlayOrPause.setOnClickListener {
            if (!isServiceConnected) {
                onClickStartService()
            } else {
                if (mService?.isPlaying() == true) {
                    mService?.pauseListening()
                } else {
                    mService?.resumeListening()
                }
                setStatusImageViewPlayOrPause()
            }
        }
        binding.btnRepeat.setOnClickListener {
            if (mService?.isRepeat() == true) {
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat)
                mService?.noRepeat()
            } else {
                binding.btnRepeat.setImageResource(R.drawable.ic_repeat2)
                mService?.repeat()
            }
        }
        binding.btnSpeed.setOnClickListener {
            if (mService?.isPlaying() == true) {
                if (mService?.isSlowMotion() == true) {
                    binding.btnSpeed.setImageResource(R.drawable.ic_1x)
                    mService?.normalSpeed()
                } else {
                    binding.btnSpeed.setImageResource(R.drawable.ic_slow_motion)
                    mService?.slowMotion()
                }
            }
        }
        binding.btnReplay.setOnClickListener {
            mService?.replay5s()
        }
        binding.btnForward.setOnClickListener {
            mService?.forward5s()
        }
        binding.ivTranslate.setOnClickListener {
            binding.llTranslateText.visibility = View.VISIBLE
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val seekBarProgress =
                        (progress.toFloat() / 100.0 * mService?.getDuration()!!).toInt()
                    mService?.seekTo(seekBarProgress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.btnBack.setOnClickListener {
            callback.backToPrevious()
        }


    }

    private fun onClickStartService() {
        loading(true)
        val i = Intent(mContext, MyService::class.java)
        val bundle = Bundle()
        bundle.putSerializable("listening", listening)
        i.putExtras(bundle)
        mContext.startService(i)

        mContext.bindService(i, connection, Context.BIND_AUTO_CREATE)

    }

    private fun onClickStopService() {
        val i = Intent(mContext, MyService::class.java)
        mContext.stopService(i)

        if (isServiceConnected) {
            mContext.unbindService(connection)
            isServiceConnected = false
        }
    }

    private fun handleLayoutListening() {
        binding.layoutBottom.visibility = View.VISIBLE
        binding.tvDuration.text = milliSecondsToTimer(mService?.getDuration()!!.toLong())
        mService?.setCallback(this)
        setStatusImageViewPlayOrPause()
    }


    private fun setStatusImageViewPlayOrPause() {
        loading(false)
        if (mService?.isPlaying() == true) {
            binding.btnPlayOrPause.setImageResource(R.drawable.ic_pause)
            updateSeekBar()
        } else {
            binding.btnPlayOrPause.setImageResource(R.drawable.ic_play)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onClickStopService()
    }


    private fun updateSeekBar() {
        val mHandler = Handler(Looper.getMainLooper())
        mHandler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    val currentPos = mService?.getCurrentPosition()
                    val duration = mService?.getDuration()
                    binding.tvCurrent.text = milliSecondsToTimer(currentPos?.toLong() ?: 0)
                    binding.seekBar.progress = currentPos?.times(100)?.div(duration ?: 1) ?: 0
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
                mHandler.postDelayed(this, 100)
            }
        }, 100)
    }

    private fun milliSecondsToTimer(milliSeconds: Long): String {
        var timerString = ""
        val secondsString: String

        val minutes = ((milliSeconds % (1000 * 60 * 60)) / (1000 * 60)).toInt()
        val seconds = ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000).toInt()
        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        timerString = "$timerString$minutes:$secondsString"
        return timerString

    }

    private fun initLayout() {
        binding.tvTitle.text = listening.title
        binding.tvContent.text = listening.content
        binding.tvTitleTranslate.text = listening.titleTranslate
        binding.tvContentTranslate.text = listening.contentTranslate
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnPlayOrPause.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnPlayOrPause.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fabTranslate)
        fab!!.visibility = View.INVISIBLE
    }

    override fun onPlayingStateChanged(isPlaying: Boolean) {
        if (isPlaying) {
            binding.btnPlayOrPause.setImageResource(R.drawable.ic_pause)
            updateSeekBar()
        } else {
            binding.btnPlayOrPause.setImageResource(R.drawable.ic_play)
        }

    }


}