package com.example.englishttcm.bookmark.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.bookmark.model.BookmarkWord
import com.example.englishttcm.bookmark.repo.BookmarkRepository

class BookmarkViewModel(application: Application): AndroidViewModel(application) {
    private var repository: BookmarkRepository

    private var _bookmarkWordList = MutableLiveData<ArrayList<BookmarkWord>>()
    val bookmarkWordList: LiveData<ArrayList<BookmarkWord>> get() = _bookmarkWordList

    init {
        repository = BookmarkRepository(application)
        _bookmarkWordList = repository.getFirebaseWordList
    }

    fun getBookmarkWordList(){
        repository.bookmarkWord()
    }

    fun speakWord(audioUrl: String){
        repository.speak(audioUrl)
    }

    fun deleteNote(id: String){
        repository.deleteNote(id)
    }

    fun updateWord(word: BookmarkWord){
        repository.updateWord(word)
    }
}