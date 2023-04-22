package com.example.englishttcm.learnzone.log.repo

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.englishttcm.learnzone.log.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.core.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AuthenticationRepository(_application: Application) {

    private var application: Application
    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>
    private var userLoggedMutableLiveData: MutableLiveData<Boolean>
    private var userMutableLiveData:MutableLiveData<User>
    private var auth: FirebaseAuth
    private val defaultImage: String = "https://firebasestorage.googleapis.com/v0/b/english-ttcm.appspot.com/o/avatar_default%2FDefault_avatar.png?alt=media&token=9f936a48-7411-4a96-b2fe-4658334fb016"
    private val storageReference: StorageReference
    private val firestoreDatabase: FirebaseFirestore

    val getFirebaseUser: MutableLiveData<FirebaseUser>
        get() = firebaseUserMutableLiveData

    val checkLogged: MutableLiveData<Boolean>
        get() = userLoggedMutableLiveData
    val getUserDetail: MutableLiveData<User>
        get() = userMutableLiveData
    init {
        application = _application
        firebaseUserMutableLiveData = MutableLiveData<FirebaseUser>()
        userMutableLiveData = MutableLiveData<User>()
        userLoggedMutableLiveData = MutableLiveData<Boolean>()
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
        storageReference = FirebaseStorage.getInstance().getReference("user_image/"+auth.currentUser?.uid)
        firestoreDatabase = FirebaseFirestore.getInstance()
    }

    fun register(email: String, password: String, name: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
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

            if(it.isSuccessful){
                userLoggedMutableLiveData.postValue(true)
//                val userId = auth.currentUser?.uid
//                val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId!!)
//
//                userRef.child("password").setValue(newPassword).addOnCompleteListener{
//                    if(it.isSuccessful){
//                        Toast.makeText(application, "Update password successfully", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
//                        Toast.makeText(application, "Update password successfully", Toast.LENGTH_SHORT).show()
//                    }
//                }
                Toast.makeText(application, "Check your email to reset your password!", Toast.LENGTH_SHORT).show()

            }
            else {
                Toast.makeText(application, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun updateUserImage(imageUri: Uri) {
        storageReference.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                firestoreDatabase.collection("users").document(auth.currentUser!!.uid)
                    .update("image", uri.toString())
                    .addOnSuccessListener {
                        Toast.makeText(application, "Upload successfully!", Toast.LENGTH_SHORT)
                            .show()
                        getUserDetail(auth.currentUser!!.uid)
                    }.addOnFailureListener { exception ->
                        Toast.makeText(application, "Error updating image", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(application, "Error uploading image", Toast.LENGTH_SHORT).show()
        }
    }


    fun updateUserName(userId: String, newName: String) {
        val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)

        userRef.update("name", newName)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(application, "Update username successfully", Toast.LENGTH_SHORT).show()
                    getUserDetail(auth.currentUser!!.uid)
                } else {
                    Toast.makeText(application, "Update username failed", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
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
//        userLoggedMutableLiveData.postValue(true)
    }


}