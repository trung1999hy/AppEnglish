package com.tpk.englishttcm.ui.dialog

import android.media.MediaPlayer
import android.view.ViewGroup
import com.tpk.englishttcm.base.BaseDialog
import com.tpk.englishttcm.ui.fragment.game.quiz.PlayQuizFragment.Companion.mediaBg
import com.tpk.englishttcm.R
import com.tpk.englishttcm.databinding.DialogWinBinding
import com.tpk.englishttcm.callback.OnBackListener

class WinQuizDialog(private var listener: OnBackListener) : BaseDialog<DialogWinBinding>() {

    private var mediaWin = MediaPlayer()

    override fun getLayout(container: ViewGroup?): DialogWinBinding =
        DialogWinBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        if(mediaBg.isPlaying){
            mediaBg.stop()
            mediaWin = MediaPlayer.create(requireContext(), R.raw.win)
            mediaWin.start()
        }
        mediaWin = MediaPlayer.create(requireContext(), R.raw.win)
        mediaWin.start()

        onBackPress()

    }

    private fun onBackPress() {
        binding.tvBack.setOnClickListener {
            listener.onBack()
            dismiss()
        }
    }

}