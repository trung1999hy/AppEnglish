package com.example.englishttcm.learnzone.log.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.learnzone.log.model.User
import com.example.englishttcm.learnzone.log.repo.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: AuthenticationRepository
    private var userData: MutableLiveData<FirebaseUser>
    private var loggedStatus: MutableLiveData<Boolean>
    private var user:MutableLiveData<User>


    init {
        repository = AuthenticationRepository(application)
        userData = repository.getFirebaseUser
        loggedStatus = repository.checkLogged
        user = repository.getUserDetail

    }


    val getUserData: MutableLiveData<FirebaseUser>
        get() = userData
    val getUserDetail: MutableLiveData<User>
        get() = user
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
    fun updateUserImage(imageUri: Uri){
        repository.updateUserImage(imageUri)
    }
    fun updateUserName(userId: String,newName:String){
        repository.updateUserName(userId,newName)
    }
    fun getUserDetail(userId:String){
        repository.getUserDetail(userId)
    }
    fun signOut(){
        repository.signOut()
    }

}