package com.example.englishttcm.learnzone.grammar

import android.view.ViewGroup
import com.example.englishttcm.base.BaseFragment
import com.tpk.englishttcm.databinding.FragmentLearnGrammarBinding

class LearnGrammarFragment : BaseFragment<FragmentLearnGrammarBinding>() {

    override fun getLayout(container: ViewGroup?): FragmentLearnGrammarBinding =
        FragmentLearnGrammarBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        // do nothing
    }
}