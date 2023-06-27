package com.tpk.englishttcm.bookmark.view.dialog

import android.view.ViewGroup
import com.tpk.englishttcm.base.BaseDialog
import com.tpk.englishttcm.bookmark.model.BookmarkWord
import com.tpk.englishttcm.learnzone.vocabulary.view.dialog.OnClickListener
import com.tpk.englishttcm.databinding.DialogUpdateWordBinding

class UpdateWordDialog(
    private val word: BookmarkWord,
    private var listener: OnClickListener): BaseDialog<DialogUpdateWordBinding>() {

    override fun getLayout(container: ViewGroup?): DialogUpdateWordBinding =
        DialogUpdateWordBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        onUpdate()
        onExit()
        onEditText(word.word, word.mean, word.speaker, word.pronounce, word.example)
    }

    private fun onEditText(word: String?, mean: String?, speaker: String?, pronounce: String?, example: String?) {
        binding.edtWord.setText(word)
        binding.edtMean.setText(mean)
        binding.edtSpeaker.setText(speaker)
        binding.edtPronounce.setText(pronounce)
        binding.edtExample.setText(example)
    }

    private fun onExit() {
        binding.btnExit.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun onUpdate() {
        binding.btnUpdate.setOnClickListener {
            listener.onClick(
                binding.edtWord.text.toString(),
                binding.edtMean.text.toString(),
                binding.edtSpeaker.text.toString(),
                binding.edtPronounce.text.toString(),
                binding.edtExample.text.toString())
            dismiss()
        }
    }


}