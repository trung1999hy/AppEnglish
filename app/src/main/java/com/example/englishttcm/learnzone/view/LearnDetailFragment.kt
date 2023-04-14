package com.example.englishttcm.learnzone.view

import android.view.ViewGroup
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentDetailLearnBinding

class LearnDetailFragment : BaseFragment<FragmentDetailLearnBinding>(){
    override fun getLayout(container: ViewGroup?): FragmentDetailLearnBinding =
        FragmentDetailLearnBinding.inflate(layoutInflater, container, false)

    override fun initViews() {

    }

}
