package com.example.englishttcm.log.view

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLogInBinding
import com.example.englishttcm.home.HomeFragment
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel
import com.example.englishttcm.until.PreferenceManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseUser

class LogInFragment : BaseFragment<FragmentLogInBinding>() {

    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var loggedCheck: MutableLiveData<Boolean>
    private lateinit var preferenceManager:PreferenceManager

    override fun getLayout(container: ViewGroup?): FragmentLogInBinding =
        FragmentLogInBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        loggedCheck = authenticationViewModel.getLoggedStatus
        preferenceManager = PreferenceManager(requireContext())



        val isRemember = preferenceManager.getBoolean("isRemember")
        if(isRemember) {
            val email = preferenceManager.getString("email")
            val password = preferenceManager.getString("password")
            authenticationViewModel.logIn(email!!,password!!,true)
            authenticationViewModel.getUserData.observe(viewLifecycleOwner){
                    user ->
                loggedCheck.observe(this){
                    if(it){
                        loading(false)
                        callback.showFragment(LogInFragment::class.java, HomeFragment::class.java, R.anim.slide_in, 0,user)
                    }
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val remember = binding.cbRemember.isChecked
            if (email.isEmpty()) {
                binding.etEmail.error = getString(R.string.empty_email)
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error = getString(R.string.valid_email)
            }
            else if (password.isEmpty()) {
                binding.etPassword.error = getString(R.string.empty_password)
            } else {
                loading(true)
                authenticationViewModel.logIn(email, password,remember)
                authenticationViewModel.getUserData.observe(viewLifecycleOwner){
                    user ->
                    loggedCheck.observe(this){
                        if(it){
                            loading(false)
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

    override fun onResume() {
        super.onResume()
        var fab = activity?.findViewById<FloatingActionButton>(R.id.fabTranslate)
        fab!!.visibility = View.INVISIBLE
    }
    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnLogin.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnLogin.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}