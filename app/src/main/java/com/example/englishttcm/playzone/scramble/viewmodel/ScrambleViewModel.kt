package com.example.englishttcm.playzone.scramble.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishttcm.playzone.scramble.model.Scramble
import com.example.englishttcm.playzone.scramble.repo.ScrambleRepository

class ScrambleViewModel: ViewModel() {

    private val scrambleRepository = ScrambleRepository()
    fun getWords(): LiveData<List<Scramble>> {
        return scrambleRepository.getWords()
    }
    fun checkTrue(aswered: String, trueAns: String): Boolean {
        return scrambleRepository.checkTrue(aswered, trueAns)
    }
    fun reinitializeData() {
        scrambleRepository.reinitializeData()
    }
    fun nextWord() {
        scrambleRepository.nextWord()
    }

//    private val _score = MutableLiveData(0)
//    val score: LiveData<Int>
//        get() = _score
//
//    private val _currentWordCount = MutableLiveData(0)
//    val currentWordCount: LiveData<Int>
//        get() = _currentWordCount
//
//    private val _scrambleWord = MutableLiveData<String>()
//    val scrambleWord: LiveData<String>
//        get() = _scrambleWord
//
//    private var wordsList: MutableList<ScrambleRepository> = mutableListOf()
//    private lateinit var currentWord: ScrambleRepository
//
//    init {
//        getNextWord()
//    }
//
//    private fun getNextWord() {
//        currentWord = wordsList.random()
//        val tempWord = currentWord.toString().toCharArray()
//        tempWord.shuffle()
//        if (wordsList.contains(currentWord)) {
//            getNextWord()
//        } else {
//            _scrambleWord.value = String(tempWord)
//            // _currentWordCount.value = _currentWordCount.value?.inc()
//            wordsList.add(currentWord)
//        }
//    }
//
//    fun  reinitializeData() {
//        _score.value = 0
//        _currentWordCount.value = 0
//        wordsList.clear()
//        getNextWord()
//    }
//
//    private fun increaseScore() {
//        _score.value = _score.value?.plus(10)
//    }

//    fun isCorrect(playerWord: String): Boolean {
//
//        checkTrue()
//    }
//
//    fun nextWord(): Boolean {
//        if (_currentWordCount.value!! < 10) {
//            return true
//        }
//        else {
//            return false
//        }
//    }

}