package com.example.englishttcm.playzone.view.fragment

import android.view.ViewGroup
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentMultichoiceSelectionBinding

class SelectTypeFragment : BaseFragment<FragmentMultichoiceSelectionBinding>(){

    override fun getLayout(container: ViewGroup?): FragmentMultichoiceSelectionBinding =
        FragmentMultichoiceSelectionBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        binding.layoutVocabularyQuiz.setOnClickListener {
            callback.showFragment(SelectTypeFragment::class.java, PlayQuizFragment::class.java,0,0, null, true)
        }
    }

}