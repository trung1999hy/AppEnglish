package com.example.englishttcm.bookmark.view.dialog

import android.view.ViewGroup
import com.example.englishttcm.base.BaseDialog
import com.example.englishttcm.databinding.DialogDeleteNoteBinding
import com.example.englishttcm.learnzone.vocabulary.view.dialog.OnClickListener

class DeleteDialog(private var listener: OnClickListener): BaseDialog<DialogDeleteNoteBinding>() {

    override fun getLayout(container: ViewGroup?): DialogDeleteNoteBinding =
        DialogDeleteNoteBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        onDeleteNote()
        onNoDelete()
    }

    private fun onNoDelete() {
        binding.btnNo.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun onDeleteNote() {
        binding.btnYes.setOnClickListener {
            listener.onClick()
            dismiss()
        }
    }
}