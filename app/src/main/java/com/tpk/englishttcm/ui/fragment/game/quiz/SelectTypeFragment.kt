package com.tpk.englishttcm.ui.fragment.game.quiz

import android.view.ViewGroup
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.databinding.FragmentMultichoiceSelectionBinding

class SelectTypeFragment : BaseFragment<FragmentMultichoiceSelectionBinding>(){

    override fun getLayout(container: ViewGroup?): FragmentMultichoiceSelectionBinding =
        FragmentMultichoiceSelectionBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        binding.layoutVocabularyQuiz.setOnClickListener {
            callback.showFragment(SelectTypeFragment::class.java, PlayQuizFragment::class.java,0,0, null, true)
        }

        binding.ivBack.setOnClickListener{
            callback.backToPrevious()
        }
    }

}