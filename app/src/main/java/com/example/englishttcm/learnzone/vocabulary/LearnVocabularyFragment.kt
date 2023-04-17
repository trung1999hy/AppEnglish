package com.example.englishttcm.learnzone.vocabulary

import android.view.ViewGroup
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLearnVocabularyBinding

class LearnVocabularyFragment : BaseFragment<FragmentLearnVocabularyBinding>() {

    override fun getLayout(container: ViewGroup?): FragmentLearnVocabularyBinding =
        FragmentLearnVocabularyBinding.inflate(layoutInflater, container, false)


    override fun initViews() {
        // do nothing
    }
}