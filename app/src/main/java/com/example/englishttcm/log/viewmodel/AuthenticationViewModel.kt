package com.example.englishttcm.log.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.log.repo.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: AuthenticationRepository
    private var userData: MutableLiveData<FirebaseUser>
    private var loggedStatus: MutableLiveData<Boolean>

    init {
        repository = AuthenticationRepository(application)
        userData = repository.getFirebaseUser
        loggedStatus = repository.checkLogged
    }

    val getUserData: MutableLiveData<FirebaseUser>
        get() = userData

    val getLoggedStatus: MutableLiveData<Boolean>
        get() = loggedStatus

    fun register(email: String, password: String, name: String){
        repository.register(email, password, name)
    }

    fun logIn(email: String, password: String){
        repository.login(email, password)
    }
    fun forgotPassword(email: String) {
        repository.forgotPassword(email)
    }

    fun signOut(){
        repository.signOut()
    }

}