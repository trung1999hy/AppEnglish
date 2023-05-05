package com.example.englishttcm.learnzone.vocabulary.view.dialog

import android.view.ViewGroup
import com.example.englishttcm.base.BaseDialog
import com.example.englishttcm.databinding.DialogNoteWordBinding

class NoteWordDialog(private var listener: OnClickListener): BaseDialog<DialogNoteWordBinding>() {

    override fun getLayout(container: ViewGroup?): DialogNoteWordBinding =
        DialogNoteWordBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        onNoteWord()
        onBackPress()
    }

    private fun onNoteWord() {
        binding.tvYes.setOnClickListener {
            listener.onClick()
            dismiss()
        }
    }

    private fun onBackPress() {
        binding.tvNo.setOnClickListener {
            dialog?.cancel()
        }
    }

}