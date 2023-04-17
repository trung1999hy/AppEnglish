package com.example.englishttcm.log.repo

import android.app.Application
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.log.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AuthenticationRepository(_application: Application) {

    private var application: Application
    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>
    private var userLoggedMutableLiveData: MutableLiveData<Boolean>
    private var auth: FirebaseAuth

    val getFirebaseUser: MutableLiveData<FirebaseUser>
        get() = firebaseUserMutableLiveData

    val checkLogged: MutableLiveData<Boolean>
        get() = userLoggedMutableLiveData

    init {
        application = _application
        firebaseUserMutableLiveData = MutableLiveData<FirebaseUser>()
        userLoggedMutableLiveData = MutableLiveData()
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }

    fun register(email: String, password: String, name: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                firebaseUserMutableLiveData.postValue(auth.currentUser)
                val dbUserRef = FirebaseDatabase.getInstance().getReference("users")
                val user = User(email, password, name, 500, 0, 0)
                dbUserRef.child(auth.uid!!).setValue(user).addOnCompleteListener {
                    Toast.makeText(application, "Update user's profile successfully", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(application, "Sign up successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(application, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                firebaseUserMutableLiveData.postValue(auth.currentUser)
                userLoggedMutableLiveData.postValue(true)
                Toast.makeText(application, "Log in successfully", Toast.LENGTH_SHORT).show()
            } else {
                userLoggedMutableLiveData.postValue(false)
                Toast.makeText(application, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signOut(){
        auth.signOut()
        //userLoggedMutableLiveData.postValue(true)
    }

}