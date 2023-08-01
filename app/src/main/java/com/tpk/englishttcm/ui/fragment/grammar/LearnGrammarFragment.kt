package com.tpk.englishttcm.ui.fragment.grammar

import android.view.ViewGroup
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.databinding.FragmentLearnGrammarBinding

class LearnGrammarFragment : BaseFragment<FragmentLearnGrammarBinding>() {

    override fun getLayout(container: ViewGroup?): FragmentLearnGrammarBinding =
        FragmentLearnGrammarBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        // do nothing
    }
}