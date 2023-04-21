package com.example.englishttcm.learnzone.vocabulary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.databinding.ItemVocabularyTopicBinding
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic

class VocabularyTopicAdapter(
    private val listVocabTopic: List<VocabularyTopic>,
    private val itemClick: OnItemClickListener
) : RecyclerView.Adapter<VocabularyTopicAdapter.VocabTopicViewHolder>() {

    inner class VocabTopicViewHolder(val binding: ItemVocabularyTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vocabTopic: VocabularyTopic) {
            binding.txtNametopic.text = vocabTopic.name
            Glide.with(itemView.context).load(vocabTopic.image).into(binding.imgTopic)
        }

        init {
            binding.rlTopic.setOnClickListener {
                itemClick.onItemClick(listVocabTopic[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabTopicViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = ItemVocabularyTopicBinding.inflate(inflate, parent, false)
        return VocabTopicViewHolder(view)
    }

    override fun getItemCount(): Int = listVocabTopic.size

    override fun onBindViewHolder(holder: VocabTopicViewHolder, position: Int) {
        holder.bind(listVocabTopic[position])
    }
}