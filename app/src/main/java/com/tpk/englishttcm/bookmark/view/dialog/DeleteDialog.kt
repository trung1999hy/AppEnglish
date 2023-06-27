package com.tpk.englishttcm.bookmark.view.dialog

import android.view.ViewGroup
import com.tpk.englishttcm.base.BaseDialog
import com.tpk.englishttcm.learnzone.vocabulary.view.dialog.OnClickListener
import com.tpk.englishttcm.databinding.DialogDeleteNoteBinding

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