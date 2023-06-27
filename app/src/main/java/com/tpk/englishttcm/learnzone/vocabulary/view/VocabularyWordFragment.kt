package com.tpk.englishttcm.learnzone.vocabulary.view

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.learnzone.vocabulary.OnItemWordClickListener
import com.tpk.englishttcm.learnzone.vocabulary.adapter.VocabularyWordAdapter
import com.tpk.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.tpk.englishttcm.learnzone.vocabulary.model.VocabularyWord
import com.tpk.englishttcm.learnzone.vocabulary.view.dialog.NoteWordDialog
import com.tpk.englishttcm.learnzone.vocabulary.view.dialog.OnClickListener
import com.tpk.englishttcm.learnzone.vocabulary.viewmodel.VocabularyViewModel
import com.tpk.englishttcm.databinding.FragmentVocabularyWordBinding


class VocabularyWordFragment : BaseFragment<FragmentVocabularyWordBinding>()  {

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

    override fun initViews() {

        viewModel.vocabTopicWord.observe(viewLifecycleOwner) {
            binding.vpgWord.adapter = VocabularyWordAdapter(it, object : OnItemWordClickListener {
                override fun onItemClick(data: Any?) {
                    val word = data as VocabularyWord
                    viewModel.speakWord(word.speaker.toString())
                }

                override fun onItemNoteClick(data: Any?) {
                    if (data is VocabularyWord) {
                        val vocabWord = data
                        val dialog = NoteWordDialog(object: OnClickListener {
                            override fun onClick() {
                                viewModel.getNoteWord(vocabWord)
                                notify("Success")
                            }

                            override fun onClick(
                                word: String,
                                mean: String,
                                speaker: String,
                                pronounce: String,
                                example: String
                            ) {
                            }
                        })
                        dialog.show(requireActivity().supportFragmentManager, "note_word_dialog")
                    } else {
                        notify("Error Data")
                    }
                }

                override fun onItemEditClick(data: Any?) {
                }

            })
        }

        binding.txtTitleTopic.text = topic.name

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

        binding.btnBackTopic.setOnClickListener {
            callback.backToPrevious()
        }

    }
}

