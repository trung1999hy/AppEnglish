package com.example.englishttcm.learnzone.vocabulary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentVocabularyWordBinding
import com.example.englishttcm.learnzone.vocabulary.adapter.VocabularyWordAdapter
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.viewmodel.VocabularyViewModel


class VocabularyWordFragment : BaseFragment<FragmentVocabularyWordBinding>(), OnItemClickListener {

    private lateinit var topic: VocabularyTopic
    private lateinit var viewModel: VocabularyViewModel

    override fun getLayout(container: ViewGroup?): FragmentVocabularyWordBinding =
        FragmentVocabularyWordBinding.inflate(layoutInflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topic = data as VocabularyTopic
        viewModel = ViewModelProvider(this)[VocabularyViewModel::class.java]
        topic.topicId?.let { viewModel.getVocabWordList(it) }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.vocabTopicWord.observe(viewLifecycleOwner){
            binding.vpgWord.adapter =
                VocabularyWordAdapter(it, this)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun initViews() {

        binding.btnNext.setOnClickListener {
            val currentItem = binding.vpgWord.currentItem
            val itemCount = binding.vpgWord.adapter?.itemCount ?: 0
            if (currentItem == itemCount - 1) {
                binding.vpgWord.setCurrentItem(0, false)
            } else {
                binding.vpgWord.setCurrentItem(currentItem + 1, true)
            }
        }
        binding.btnPrev.setOnClickListener {
            val currentItem = binding.vpgWord.currentItem
            val itemCount = binding.vpgWord.adapter?.itemCount ?: 0
            if (currentItem == 0) {
                binding.vpgWord.setCurrentItem(itemCount - 1, false)
            } else {
                binding.vpgWord.setCurrentItem(currentItem - 1, true)
            }
        }

        binding.btnBackTopic.setOnClickListener{
            callback.backToPrevious()
        }

    }
    override fun onItemClick(data: Any?) {
    }
}

