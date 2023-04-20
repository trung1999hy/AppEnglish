package com.example.englishttcm.learnzone.vocabulary.view

import android.content.ContentValues.TAG
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLearnVocabularyBinding
import com.example.englishttcm.home.HomeFragment
import com.example.englishttcm.learnzone.vocabulary.adapter.VocabTopicAdapter
import com.example.englishttcm.learnzone.vocabulary.adapter.VocabularyWordAdapter
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LearnVocabularyFragment : BaseFragment<FragmentLearnVocabularyBinding>() {
    private lateinit var topicList : ArrayList<VocabularyTopic>
    private var db = Firebase.firestore
    private lateinit var topic:VocabularyTopic

    override fun getLayout(container: ViewGroup?): FragmentLearnVocabularyBinding =
        FragmentLearnVocabularyBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        topicList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        db.collection("vocabularyTopic").get()
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful && task.result != null){
                    for(document in task.result){
                        val topicId = document.id.toInt()
                        val name = document.getString("name")
                        val image = document.getString("image")
                        topic = VocabularyTopic(topicId,name,image)
                        topicList.add(topic)
                    }
                    binding.rcvTopic.adapter = VocabTopicAdapter(topicList,requireActivity(), object : OnItemClickListener {
                        override fun onItemClick(data: Any?) {
                            topic = data as VocabularyTopic
                            callback.showFragment(LearnVocabularyFragment::class.java,VocabularyWordFragment::class.java,0,0,topic,true)
                        }
                    })
                }

            }
            .addOnFailureListener {
                notify("Error")
            }
        binding.btnBack.setOnClickListener{
            callback.backToPrevious()
        }

    }

}
