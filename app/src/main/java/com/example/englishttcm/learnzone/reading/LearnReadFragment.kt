package com.example.englishttcm.learnzone.reading

import android.view.ViewGroup
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLearnReadingBinding

class LearnReadFragment : BaseFragment<FragmentLearnReadingBinding>(){

    override fun getLayout(container: ViewGroup?): FragmentLearnReadingBinding =
        FragmentLearnReadingBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        // do nothing
    }

}