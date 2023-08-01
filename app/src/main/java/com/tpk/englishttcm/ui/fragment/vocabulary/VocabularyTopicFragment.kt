package com.tpk.englishttcm.ui.fragment.vocabulary

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tpk.englishttcm.callback.OnItemClickListener
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.ui.adapter.VocabularyTopicAdapter
import com.tpk.englishttcm.data.remote.firebase.model.VocabularyTopic
import com.tpk.englishttcm.viewmodel.VocabularyViewModel
import com.tpk.englishttcm.databinding.FragmentVocabularyTopicBinding
import java.util.*

class VocabularyTopicFragment : BaseFragment<FragmentVocabularyTopicBinding>() {

    private lateinit var viewModel: VocabularyViewModel
    private lateinit var topicAdapter: VocabularyTopicAdapter
    private var originalList: List<VocabularyTopic> = emptyList()

    override fun getLayout(container: ViewGroup?): FragmentVocabularyTopicBinding =
        FragmentVocabularyTopicBinding.inflate(layoutInflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[VocabularyViewModel::class.java]
        viewModel.getVocabTopicList()
    }

    override fun initViews() {
        binding.searchTopic.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                topicAdapter.filter.filter(p0)

                val searchText = p0.toString().toLowerCase(Locale.getDefault())
                if(searchText.isBlank()){
                    viewModel.vocabTopicList.observe(viewLifecycleOwner) { originalList ->
                        if (originalList != null) {
                            val onItemClickListener = object : OnItemClickListener {
                                override fun onItemClick(data: Any?) {
                                    val topic = data as VocabularyTopic
                                    callback.showFragment(
                                        VocabularyTopicFragment::class.java,
                                        VocabularyWordFragment::class.java,
                                        0,
                                        0,
                                        topic,
                                        true
                                    )
                                }
                            }
                            topicAdapter = VocabularyTopicAdapter(originalList, onItemClickListener)
                            binding.rcvTopic.adapter = topicAdapter
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        viewModel.vocabTopicList.observe(viewLifecycleOwner) { topicList ->
            if (topicList != null) {
                val onItemClickListener = object : OnItemClickListener {
                    override fun onItemClick(data: Any?) {
                        val topic = data as VocabularyTopic
                        callback.showFragment(
                            VocabularyTopicFragment::class.java,
                            VocabularyWordFragment::class.java,
                            0,
                            0,
                            topic,
                            true
                        )
                    }
                }
                originalList = topicList
                topicAdapter = VocabularyTopicAdapter(topicList, onItemClickListener)
                binding.rcvTopic.adapter = topicAdapter
            }
        }


        binding.btnBack.setOnClickListener {
            callback.backToPrevious()
        }
    }

}
