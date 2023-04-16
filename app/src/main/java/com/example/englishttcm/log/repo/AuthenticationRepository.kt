package com.example.englishttcm.log.repo

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
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
        userLoggedMutableLiveData = MutableLiveData<Boolean>()
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
                    Log.d("UpdateUser","Update user's profile successfully")
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
                Toast.makeText(application, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }



    fun forgotPassword(email: String){
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful()){
                Toast.makeText(application, "Check your email to reset your password!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(application, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun signOut(){
        auth.signOut()
        //userLoggedMutableLiveData.postValue(true)
    }


}