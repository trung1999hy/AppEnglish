package com.tpk.englishttcm.ui.fragment.reading

import android.view.ViewGroup
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.databinding.FragmentLearnReadingBinding

class LearnReadFragment : BaseFragment<FragmentLearnReadingBinding>(){

    override fun getLayout(container: ViewGroup?): FragmentLearnReadingBinding =
        FragmentLearnReadingBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        // do nothing
    }

}