package com.example.englishttcm.learnzone.vocabulary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishttcm.OnActionCallback
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.databinding.ItemVocabularyTopicBinding
import com.example.englishttcm.learnzone.model.VocabularyTopic
import com.example.englishttcm.learnzone.viewmodel.VocabTopicViewModel

class VocabTopicAdapter(
    private val vocabTopicVM : VocabTopicViewModel,
    private val listVocabTopic : ArrayList<VocabularyTopic>,
    private val itemClick: OnItemClickListener
) : RecyclerView.Adapter<VocabTopicAdapter.VocabTopicViewHolder>(){

    inner class VocabTopicViewHolder(val binding: ItemVocabularyTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vocabTopic: VocabularyTopic) {
            binding.txtNametopic.text = vocabTopic.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabTopicViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = ItemVocabularyTopicBinding.inflate(inflate, parent, false)
        return VocabTopicViewHolder(view)
    }

    override fun getItemCount(): Int = listVocabTopic.size

    override fun onBindViewHolder(holder: VocabTopicViewHolder, position: Int) {
        holder.bind(listVocabTopic.get(position))
    }
}