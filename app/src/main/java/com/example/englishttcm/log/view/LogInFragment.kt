package com.example.englishttcm.log.view

import android.util.Log
import android.util.Patterns
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLogInBinding
import com.example.englishttcm.home.HomeFragment
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.FirebaseUser

class LogInFragment : BaseFragment<FragmentLogInBinding>() {

    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var loggedCheck: MutableLiveData<Boolean>

    override fun getLayout(container: ViewGroup?): FragmentLogInBinding =
        FragmentLogInBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        loggedCheck = authenticationViewModel.getLoggedStatus
        authenticationViewModel.getUserData.observe(this) { user ->
            binding.btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                if (email.isEmpty()) {
                    binding.etEmail.error = getString(R.string.empty_email)
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.etEmail.error = getString(R.string.valid_email)
                }
                else if (password.isEmpty()) {
                    binding.etPassword.error = getString(R.string.empty_password)
                } else {
                    authenticationViewModel.logIn(email, password)
                    loggedCheck.observe(this){
                        if(it){
                            callback.showFragment(LogInFragment::class.java, HomeFragment::class.java, R.anim.slide_in, 0,user)
                        }
                    }
                }
            }
        }



        binding.tvSignUp.setOnClickListener {
            callback.showFragment(LogInFragment::class.java, SignUpFragment::class.java, R.anim.slide_in, 0)
        }
        binding.tvResetPassword.setOnClickListener{
            callback.showFragment(LogInFragment::class.java, ForgotPasswordFragment::class.java, R.anim.slide_in, 0)

        }
    }
}