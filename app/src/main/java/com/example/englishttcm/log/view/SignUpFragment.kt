package com.example.englishttcm.log.view

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentSignUpBinding
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(){

    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun getLayout(container: ViewGroup?): FragmentSignUpBinding =
        FragmentSignUpBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        authenticationViewModel.getUserData.observe(this) {}

        binding.btnSignUp.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val name = binding.edtName.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()){
                authenticationViewModel.register(email, password, name)
            }

        }

    }

}