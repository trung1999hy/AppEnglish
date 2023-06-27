package com.tpk.englishttcm.learnzone.vocabulary.view.dialog

interface OnClickListener {
    fun onClick()

    fun onClick(word: String, mean:String, speaker: String, pronounce: String, example: String)
}