package com.example.englishttcm.playzone

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentPlayMultiChoiceBinding
import com.example.englishttcm.db.EnglishViewModel

class PlayQuizFragment : BaseFragment<FragmentPlayMultiChoiceBinding>() {

    private lateinit var englishViewModel: EnglishViewModel

    override fun getLayout(container: ViewGroup?): FragmentPlayMultiChoiceBinding =
        FragmentPlayMultiChoiceBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        englishViewModel = ViewModelProvider(this)[EnglishViewModel::class.java]

        englishViewModel.readAllQuestionVocab.observe(viewLifecycleOwner, Observer {
            Log.i("test_db,", it.toString())
        })
    }

}