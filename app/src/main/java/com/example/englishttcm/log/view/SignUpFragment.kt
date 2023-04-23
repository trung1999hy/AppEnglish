package com.example.englishttcm.log.view

import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentSignUpBinding
import com.example.englishttcm.home.HomeFragment
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(){

    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun getLayout(container: ViewGroup?): FragmentSignUpBinding =
        FragmentSignUpBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if(name.isEmpty()){
                binding.etName.error = getString(R.string.empty_name)
            }
            else if(email.isEmpty()){
                binding.etEmail.error = getString(R.string.empty_email)
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error = getString(R.string.valid_email)
            }
            else if(password.isEmpty()){
                binding.etPassword.error = getString(R.string.empty_password)
            } else if(password.length < 6){
                binding.etPassword.error = getString(R.string.counter_length)
            } else if (confirmPassword.isEmpty()) {
                binding.etConfirmPassword.error = getString(R.string.empty_cpassword)
            } else if (!password.equals(confirmPassword)) {
                binding.etConfirmPassword.error = getString(R.string.same_password)
            }else {
                loading(true)
                authenticationViewModel.register(email, password, name)
                authenticationViewModel.getLoggedStatus.observe(this){
                    if(it){
                        loading(false)
                        callback.showFragment(SignUpFragment::class.java, LogInFragment::class.java, R.anim.slide_in, 0)
                    }
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            callback.showFragment(SignUpFragment::class.java, LogInFragment::class.java, R.anim.slide_out,0)
        }

    }
    override fun onResume() {
        super.onResume()
        var fab = activity?.findViewById<FloatingActionButton>(R.id.fabTranslate)
        fab!!.visibility = View.INVISIBLE
    }
    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnSignUp.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnSignUp.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}