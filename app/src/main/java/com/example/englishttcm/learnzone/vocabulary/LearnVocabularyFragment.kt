package com.example.englishttcm.learnzone.vocabulary

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLearnVocabularyBinding
import com.example.englishttcm.learnzone.model.VocabularyTopic
import com.example.englishttcm.learnzone.viewmodel.VocabTopicViewModel
import com.example.englishttcm.learnzone.vocabulary.adapter.VocabTopicAdapter

class LearnVocabularyFragment : BaseFragment<FragmentLearnVocabularyBinding>() {
    private lateinit var vocabViewModel: VocabTopicViewModel
    override fun getLayout(container: ViewGroup?): FragmentLearnVocabularyBinding =
        FragmentLearnVocabularyBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        vocabViewModel = ViewModelProvider(this)[VocabTopicViewModel::class.java]
        vocabViewModel.getListVocabTopic().observe(this) { listVocabTopicLive ->
            binding.rcvTopic.adapter =
                VocabTopicAdapter(vocabViewModel, listVocabTopicLive, object :
                    OnItemClickListener {
                    override fun onItemClick(data: Any?) {
                        val vocabTopic = data as VocabularyTopic
                    }
                })
        }
    }
}