package com.example.englishttcm.log.view

import android.view.ViewGroup
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLogInBinding

class LogInFragment : BaseFragment<FragmentLogInBinding>(){

    override fun getLayout(container: ViewGroup?): FragmentLogInBinding =
        FragmentLogInBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        //do nothing
    }
}