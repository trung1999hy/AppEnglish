package com.example.englishttcm.learnzone.learning

import android.view.ViewGroup
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLearnListeningBinding
import com.example.englishttcm.home.model.GamePlayMode
import com.example.englishttcm.home.model.StudyMode

class LearnListenFragment : BaseFragment<FragmentLearnListeningBinding>(){

    override fun getLayout(container: ViewGroup?): FragmentLearnListeningBinding =
        FragmentLearnListeningBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        val study: StudyMode = data as StudyMode
        binding.tvTest.text = study.image.toString()
    }
}