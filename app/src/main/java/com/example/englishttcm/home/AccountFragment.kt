package com.example.englishttcm.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentAccountBinding
import com.example.englishttcm.log.model.User
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class AccountFragment : BaseFragment<FragmentAccountBinding>() {


    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var firebaseUser: FirebaseUser
    private var imageUri: Uri? = null



    override fun getLayout(container: ViewGroup?): FragmentAccountBinding = FragmentAccountBinding.inflate(layoutInflater,container,false)


    override fun initViews() {
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        firebaseUser = data as FirebaseUser
        authenticationViewModel.getUserDetail(firebaseUser.uid)


        authenticationViewModel.getUserDetail.observe(viewLifecycleOwner){
            binding.etName.setText(it.name)
            binding.tvEmail.text = it.email
            Glide.with(requireActivity())
                .load(it.image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivProfile)
        }


        binding.btnChangeImage.setOnClickListener{
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickImage.launch(i)
        }
        binding.btnChangeName.setOnClickListener{
            authenticationViewModel.updateUserName(firebaseUser.uid,binding.etName.text.toString())
        }
    }



    private val pickImage = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                imageUri = result.data!!.data
                authenticationViewModel.updateUserImage(imageUri!!)
            }
        }
    }



}