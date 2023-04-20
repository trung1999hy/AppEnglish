package com.example.englishttcm.learnzone.vocabulary.view

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentVocabularyWordBinding
import com.example.englishttcm.learnzone.vocabulary.adapter.VocabularyWordAdapter
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.viewmodel.VocabWordViewModel

class VocabularyWordFragment : BaseFragment<FragmentVocabularyWordBinding>() {
    private lateinit var vocabWordViewModel: VocabWordViewModel
    override fun getLayout(container: ViewGroup?): FragmentVocabularyWordBinding =
        FragmentVocabularyWordBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        vocabWordViewModel = ViewModelProvider(this)[VocabWordViewModel::class.java]
        val topic = data as VocabularyTopic
        binding.txtTitleTopic.text = topic.name
        binding.btnNext.setOnClickListener {
            val currentItem = binding.vpgWord.currentItem
            val count = binding.vpgWord.adapter?.itemCount ?: 0
            if (currentItem == count - 1) {
                binding.vpgWord.setCurrentItem(0, false)
            } else {
                binding.vpgWord.setCurrentItem(currentItem + 1, true)
            }
        }
        binding.btnPrev.setOnClickListener {
            val currentItem = binding.vpgWord.currentItem
            val count = binding.vpgWord.adapter?.itemCount ?: 0
            if (currentItem == 0) {
                binding.vpgWord.setCurrentItem(count - 1, false)
            } else {
                binding.vpgWord.setCurrentItem(currentItem - 1, true)
            }
        }
        vocabWordViewModel.getListVocabTopic().observe(viewLifecycleOwner) {
            binding.vpgWord.adapter =
                VocabularyWordAdapter(it, requireContext(), object : OnItemClickListener {
                    override fun onItemClick(data: Any?) {

                    }
                })
        }
    }
}