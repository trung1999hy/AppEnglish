package com.example.englishttcm.learnzone.vocabulary.view

import android.view.ViewGroup
import com.example.englishttcm.OnActionCallback
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentVocabularyWordBinding
import com.example.englishttcm.learnzone.vocabulary.adapter.VocabularyWordAdapter
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class VocabularyWordFragment : BaseFragment<FragmentVocabularyWordBinding>(), OnItemClickListener {

    private lateinit var vocabList : ArrayList<VocabularyWord>
    private lateinit var topic:VocabularyTopic

    private var db = Firebase.firestore
    override fun getLayout(container: ViewGroup?): FragmentVocabularyWordBinding =
        FragmentVocabularyWordBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        topic = data as VocabularyTopic
        notify(topic.topicId.toString())
        val wordsRef = db.collection("vocabularyWord")
        vocabList = arrayListOf()
        wordsRef
            .whereEqualTo("topicId", topic.topicId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    for (document in task.result!!) {
                        val id = document.id
                        val word = document.getString("word")
                        val mean = document.getString("mean")
                        val speaker = document.getString("speaker")
                        val pronounce = document.getString("pronounce")
                        val topicId = document.getLong("topicId")!!.toInt()
                        val image = document.getString("image")
                        val example = document.getString("example")
                        val vocab = VocabularyWord(
                            id,
                            word,
                            mean,
                            speaker,
                            pronounce,
                            topicId,
                            image,
                            example
                        )
                        vocabList.add(vocab)
                    }
                    binding.vpgWord.adapter =
                        VocabularyWordAdapter(vocabList, requireContext(), this)

                } else {
                    notify("Error")
                }
            }
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

        binding.btnBackTopic.setOnClickListener{
            callback.showFragment(VocabularyWordFragment::class.java,
            LearnVocabularyFragment::class.java, 0, 0, null, true)
        }
    }


    override fun onItemClick(data: Any?) {
    }




}
