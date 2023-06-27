package com.tpk.englishttcm.playzone.view.dialog

import android.media.MediaPlayer
import android.view.ViewGroup
import com.tpk.englishttcm.base.BaseDialog
import com.tpk.englishttcm.playzone.view.fragment.PlayQuizFragment.Companion.mediaBg
import com.tpk.englishttcm.R
import com.tpk.englishttcm.databinding.DialogWinBinding

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