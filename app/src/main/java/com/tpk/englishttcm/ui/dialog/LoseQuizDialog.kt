package com.tpk.englishttcm.ui.dialog

import android.media.MediaPlayer
import android.view.ViewGroup
import com.tpk.englishttcm.base.BaseDialog
import com.tpk.englishttcm.ui.fragment.game.quiz.PlayQuizFragment.Companion.mediaBg
import com.tpk.englishttcm.R
import com.tpk.englishttcm.databinding.DialogLoseBinding
import com.tpk.englishttcm.callback.OnBackListener

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