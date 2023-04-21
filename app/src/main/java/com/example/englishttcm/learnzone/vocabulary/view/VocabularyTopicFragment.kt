package com.example.englishttcm.learnzone.vocabulary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentVocabularyTopicBinding
import com.example.englishttcm.learnzone.vocabulary.adapter.VocabularyTopicAdapter
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.viewmodel.VocabularyViewModel
import com.example.englishttcm.log.viewmodel.AuthenticationViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VocabularyTopicFragment : BaseFragment<FragmentVocabularyTopicBinding>() {

    private lateinit var viewModel: VocabularyViewModel

    override fun getLayout(container: ViewGroup?): FragmentVocabularyTopicBinding =
        FragmentVocabularyTopicBinding.inflate(layoutInflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[VocabularyViewModel::class.java]
        viewModel.getVocabTopicList()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.vocabTopicList.observe(viewLifecycleOwner){
            if(it!=null){
                binding.rcvTopic.adapter = VocabularyTopicAdapter(it, object : OnItemClickListener {
                    override fun onItemClick(data: Any?) {
                        var topic = data as VocabularyTopic
                        callback.showFragment(VocabularyTopicFragment::class.java,
                            VocabularyWordFragment::class.java,0,0,topic,true)
                    }
                })
            }

        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun initViews() {
        binding.btnBack.setOnClickListener{
            callback.backToPrevious()
        }

    }

}