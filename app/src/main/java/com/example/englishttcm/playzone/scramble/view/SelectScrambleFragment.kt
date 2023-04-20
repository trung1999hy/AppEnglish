package com.example.englishttcm.playzone.scramble.view

import android.view.ViewGroup
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentMultichoiceSelectionBinding
import com.example.englishttcm.databinding.FragmentScrambleGameBinding

class SelectScrambleFragment : BaseFragment<FragmentScrambleGameBinding>(){

    override fun getLayout(container: ViewGroup?): FragmentScrambleGameBinding =
        FragmentScrambleGameBinding.inflate(layoutInflater, container, false)

    override fun initViews() {

    }

}