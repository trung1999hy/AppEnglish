package com.tpk.englishttcm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tpk.englishttcm.repo.TranslateRepository

class TranslateViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : TranslateRepository


    init {
        repository = TranslateRepository(application)

    }
    fun getTranslateText(srcLangId: Int, tarLangId:Int, sourceText: String):MutableLiveData<String> =
         repository.getTranslateText(srcLangId,tarLangId,sourceText)

}