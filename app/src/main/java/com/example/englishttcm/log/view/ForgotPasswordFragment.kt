package com.example.englishttcm.log.view

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentForgotPasswordBinding
import com.example.englishttcm.databinding.FragmentLogInBinding
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun getLayout(container: ViewGroup?): FragmentForgotPasswordBinding
    = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        binding.btnResetPassword.setOnClickListener{
            val email = binding.etEmail.text.toString()
            if (email.isEmpty()) {
                binding.etEmail.error = getString(R.string.empty_email)
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error = getString(R.string.valid_email)
            }
            else{
                authenticationViewModel.forgotPassword(email)
            }
        }
        binding.btnLogin.setOnClickListener {
            callback.showFragment(SignUpFragment::class.java, LogInFragment::class.java, R.anim.slide_out,0)
        }
    }
    override fun onResume() {
        super.onResume()
        var fab = activity?.findViewById<FloatingActionButton>(R.id.fabTranslate)
        fab!!.visibility = View.INVISIBLE
    }

}