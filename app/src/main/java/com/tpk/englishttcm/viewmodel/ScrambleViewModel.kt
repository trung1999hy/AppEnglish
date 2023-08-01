package com.tpk.englishttcm.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tpk.englishttcm.data.remote.firebase.model.Scramble
import com.tpk.englishttcm.repo.ScrambleRepository

class ScrambleViewModel : ViewModel() {

    private val scrambleRepository = ScrambleRepository()
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _currentWordCount = MutableLiveData<Int>()
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount
    private val _isOriginal = MutableLiveData<Boolean>()
    val isOriginal: LiveData<Boolean>
        get() = _isOriginal
    private val _isWin = MutableLiveData<Boolean>()
    val isWin: LiveData<Boolean>
        get() = _isWin

    init {
        _score.value = 0
        _currentWordCount.value = 0
    }

    fun getWords(): LiveData<List<Scramble>> {
        return scrambleRepository.getWords()
    }

    fun checkTrue(answered: String, trueAns: String) {
        if (answered.trim() == trueAns.trim()) {
            _score.value = _score.value?.plus(10)
            _currentWordCount.value = _currentWordCount.value?.plus(1)
            _isOriginal.value = true
        } else {
            _isOriginal.value = false
        }
    }

    fun checkWin() {
        _isWin.value = _score.value == 100
    }

    fun isSkip() {
        _isOriginal.value = true
    }


}