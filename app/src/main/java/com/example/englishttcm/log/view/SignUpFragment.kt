package com.example.englishttcm.log.view

import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentSignUpBinding
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(){

    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var loggedCheck: MutableLiveData<Boolean>

    override fun getLayout(container: ViewGroup?): FragmentSignUpBinding =
        FragmentSignUpBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        authenticationViewModel.getUserData.observe(this) {}

        binding.btnSignUp.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val name = binding.edtName.text.toString()

            if(email.isEmpty()){
                binding.edtEmail.error = getString(R.string.empty_email)
            } else if(password.isEmpty()){
                binding.edtPassword.error = getString(R.string.empty_password)
            } else if(password.length < 6){
                binding.edtPassword.error = getString(R.string.counter_length)
            } else if(name.isEmpty()){
                binding.edtName.error = getString(R.string.empty_name)
            } else {
                authenticationViewModel.register(email, password, name)
            }
        }

        binding.logInNow.setOnClickListener {
            callback.showFragment(SignUpFragment::class.java, LogInFragment::class.java, R.anim.slide_out,0)
        }

    }

}