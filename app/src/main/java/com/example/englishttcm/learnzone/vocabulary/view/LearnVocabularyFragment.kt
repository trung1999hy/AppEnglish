package com.example.englishttcm.learnzone.vocabulary.view

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLearnVocabularyBinding
import com.example.englishttcm.db.EnglishViewModel
import com.example.englishttcm.home.HomeFragment
import com.example.englishttcm.learnzone.vocabulary.adapter.VocabTopicAdapter
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord

class LearnVocabularyFragment : BaseFragment<FragmentLearnVocabularyBinding>() {
    private lateinit var englishViewModel : EnglishViewModel
    override fun getLayout(container: ViewGroup?): FragmentLearnVocabularyBinding =
        FragmentLearnVocabularyBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        englishViewModel = ViewModelProvider(this)[EnglishViewModel::class.java]
        englishViewModel.readAllTopicVocab.observe(viewLifecycleOwner) {
            binding.rcvTopic.adapter = VocabTopicAdapter(it, requireContext() ,object : OnItemClickListener{
                override fun onItemClick(data: Any?) {
                    val topic = data as VocabularyTopic
                    callback.showFragment(
                        LearnVocabularyFragment::class.java,
                        VocabularyWordFragment::class.java,
                        0,
                        0,
                        topic.topicId,
                        true
                    )
                }
            })
        }
    }
}