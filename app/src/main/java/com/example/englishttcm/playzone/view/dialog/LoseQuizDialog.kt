package com.example.englishttcm.playzone.view.dialog

import android.media.MediaPlayer
import android.view.ViewGroup
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseDialog
import com.example.englishttcm.databinding.DialogLoseBinding
import com.example.englishttcm.playzone.view.fragment.PlayQuizFragment.Companion.mediaBg

class LoseQuizDialog(private var listener: OnBackListener) : BaseDialog<DialogLoseBinding>(){

    private var mediaLose = MediaPlayer()

    override fun initViews() {
        if(mediaBg.isPlaying){
            mediaBg.stop()
            mediaLose = MediaPlayer.create(requireContext(), R.raw.game_over)
            mediaLose.start()
        }
        mediaLose = MediaPlayer.create(requireContext(), R.raw.game_over)
        mediaLose.start()

        onBackPress()

    }

    private fun onBackPress() {
        binding.tvBack.setOnClickListener {
            listener.onBack()
            dismiss()
        }
    }

    override fun getLayout(container: ViewGroup?): DialogLoseBinding =
        DialogLoseBinding.inflate(layoutInflater, container, false)
}