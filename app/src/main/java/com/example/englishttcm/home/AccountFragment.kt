package com.example.englishttcm.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.CustomDialogLoadingBinding
import com.example.englishttcm.databinding.FragmentAccountBinding
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseUser

class AccountFragment : BaseFragment<FragmentAccountBinding>() {


    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var loggedCheck: MutableLiveData<Boolean>
    private var imageUri: Uri? = null
    private var loadingDialog: AlertDialog? = null



    override fun getLayout(container: ViewGroup?): FragmentAccountBinding = FragmentAccountBinding.inflate(layoutInflater,container,false)


    @SuppressLint("ClickableViewAccessibility")
    override fun initViews() {
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        loggedCheck = authenticationViewModel.getLoggedStatus
        firebaseUser = data as FirebaseUser
        authenticationViewModel.getUserDetail(firebaseUser.uid)


        authenticationViewModel.getUserDetail.observe(viewLifecycleOwner){
            binding.etName.setText(it.name)
            binding.etEmail.setText(it.email)
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
        binding.etName.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEndWidth = binding.etName.compoundDrawables[2].bounds.width()
                if (event.rawX >= (binding.etName.right - drawableEndWidth)) {
                    authenticationViewModel.updateUserName(firebaseUser.uid,binding.etName.text.toString())
                    showDialog(true)
                    authenticationViewModel.getLoggedStatus.observe(viewLifecycleOwner){
                        if(it){
                            showDialog(false)
                        }

                    }

                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
        binding.ivBack.setOnClickListener{
            callback.backToPrevious()
        }
    }



    private val pickImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                showDialog(true)
                imageUri = result.data!!.data
                authenticationViewModel.updateUserImage(imageUri!!)
                authenticationViewModel.getLoggedStatus.observe(viewLifecycleOwner){
                    if(it){
                        showDialog(false)
                    }
                }

            }
        }
    }
    override fun onResume() {
        super.onResume()
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fabTranslate)
        fab!!.visibility = View.INVISIBLE
    }
    private fun showDialog(show:Boolean) {
        if (show) {
            val builder = AlertDialog.Builder(requireContext())
            val inflater = LayoutInflater.from(requireContext())
            val binding = CustomDialogLoadingBinding.inflate(inflater)
            builder.setView(binding.root)
            loadingDialog = builder.create()
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }

}