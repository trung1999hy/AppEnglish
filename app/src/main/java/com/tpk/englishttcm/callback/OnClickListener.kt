package com.tpk.englishttcm.callback

interface OnClickListener {
    fun onClick()

    fun onClick(word: String, mean:String, speaker: String, pronounce: String, example: String)
}