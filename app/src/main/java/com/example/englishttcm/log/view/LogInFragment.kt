package com.example.englishttcm.log.view

import android.util.Log
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
        authenticationViewModel.getUserData.observe(this) { user ->
            binding.btnLogIn.setOnClickListener {
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                if (email.isEmpty()) {
                    binding.edtEmail.error = getString(R.string.empty_email)
                } else if (password.isEmpty()) {
                    binding.edtPassword.error = getString(R.string.empty_password)
                } else {
                    authenticationViewModel.logIn(email, password)
                    callback.showFragment(LogInFragment::class.java, HomeFragment::class.java, R.anim.slide_in, 0, user)
                }
            }
        }


        binding.signUpNow.setOnClickListener {
            callback.showFragment(LogInFragment::class.java, SignUpFragment::class.java, R.anim.slide_in, 0)
        }
    }
}