package com.example.englishttcm.log.repo

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.log.model.User
import com.example.englishttcm.until.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AuthenticationRepository(_application: Application) {

    private var application: Application
    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>
    private var userLoggedMutableLiveData: MutableLiveData<Boolean>
    private var userImageUpdated: MutableLiveData<Boolean>
    private var userNameUpdated: MutableLiveData<Boolean>
    private var userMutableLiveData:MutableLiveData<User>
    private var auth: FirebaseAuth
    private val defaultImage: String = "https://firebasestorage.googleapis.com/v0/b/english-ttcm.appspot.com/o/avatar_default%2FDefault_avatar.png?alt=media&token=9f936a48-7411-4a96-b2fe-4658334fb016"
    private val storageReference: StorageReference
    private val firestoreDatabase: FirebaseFirestore
    private val preferenceManager:PreferenceManager

    val getFirebaseUser: MutableLiveData<FirebaseUser>
        get() = firebaseUserMutableLiveData

    val checkLogged: MutableLiveData<Boolean>
        get() = userLoggedMutableLiveData
    val getUserDetail: MutableLiveData<User>
        get() = userMutableLiveData
    val checkUserNameUpdated: MutableLiveData<Boolean>
        get() = userNameUpdated
    val checkUserImageUpdated: MutableLiveData<Boolean>
        get() = userImageUpdated
    init {
        application = _application
        firebaseUserMutableLiveData = MutableLiveData<FirebaseUser>()
        userMutableLiveData = MutableLiveData<User>()
        userLoggedMutableLiveData = MutableLiveData<Boolean>()
        userImageUpdated = MutableLiveData<Boolean>()
        userNameUpdated = MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
        storageReference = FirebaseStorage.getInstance().getReference("user_image/"+auth.currentUser?.uid)
        firestoreDatabase = FirebaseFirestore.getInstance()
        preferenceManager = PreferenceManager(application)
    }

    fun register(email: String, password: String, name: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->
            if(it.isSuccessful){
                firebaseUserMutableLiveData.postValue(auth.currentUser)
                userLoggedMutableLiveData.postValue(true)
                val userCollection = firestoreDatabase.collection("users")
                val user = User(email, password, name, defaultImage,500, 0, 0)
                userCollection.document(auth.uid!!).set(user).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(application, "Update user's profile successfully", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(application, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        Log.e("Firestore", "Error setting user's profile", it.exception)

                    }
                    Toast.makeText(application, "Update user's profile successfully", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(application, "Sign up successfully", Toast.LENGTH_SHORT).show()
            } else {
                userLoggedMutableLiveData.postValue(false)
                Toast.makeText(application, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(email: String, password: String, remember:Boolean){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                firebaseUserMutableLiveData.postValue(auth.currentUser)
                userLoggedMutableLiveData.postValue(true)
                Toast.makeText(application, "Log in successfully", Toast.LENGTH_SHORT).show()
                if(remember){
                    auth.currentUser.let {
                        preferenceManager.putBoolean("isRemember", true)
                        preferenceManager.putString("email",email)
                        preferenceManager.putString("password",password)
                    }
                }
            } else {
                userLoggedMutableLiveData.postValue(false)
                Toast.makeText(application, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }



    fun forgotPassword(email: String){

        auth.sendPasswordResetEmail(email).addOnCompleteListener {

            if(it.isSuccessful){
                Toast.makeText(application, "Check your email to reset your password!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(application, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun updateUserImage(imageUri: Uri) {
        storageReference.putFile(imageUri).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                firestoreDatabase.collection("users").document(auth.currentUser!!.uid)
                    .update("image", uri.toString())
                    .addOnSuccessListener {
                        userImageUpdated.postValue(true)
                        Toast.makeText(application, "Upload successfully!", Toast.LENGTH_SHORT).show()
                        getUserDetail(auth.currentUser!!.uid)
                    }.addOnFailureListener {
                        userImageUpdated.postValue(false)
                        Toast.makeText(application, "Error updating image", Toast.LENGTH_SHORT).show()
                    }
            }
        }.addOnFailureListener {
            Toast.makeText(application, "Error uploading image", Toast.LENGTH_SHORT).show()
        }
    }


    fun updateUserName(userId: String, newName: String) {
        val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)

        userRef.update("name", newName)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userNameUpdated.postValue(true)
                    Toast.makeText(application, "Update username successfully", Toast.LENGTH_SHORT).show()
                    getUserDetail(auth.currentUser!!.uid)
                } else {
                    Toast.makeText(application, "Update username failed", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                userNameUpdated.postValue(false)
                Toast.makeText(application, "Error updating username", Toast.LENGTH_SHORT).show()
            }
    }


    fun getUserDetail(userId: String) {
        val reference = firestoreDatabase.collection("users").document(userId)
        reference.get().addOnSuccessListener { documentSnapshot ->
            if(documentSnapshot.exists()) {
                val user = documentSnapshot.toObject(User::class.java)
                userMutableLiveData.postValue(user)
            }
        }.addOnFailureListener { exception ->
            Log.d("getUserDetail", "Error getting user detail: $exception")
        }
    }



    fun signOut(){
        auth.signOut()
        preferenceManager.clear()
    }


}