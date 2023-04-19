package com.example.englishttcm.learnzone.vocabulary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.databinding.ItemVocabularyWordBinding
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord

class VocabularyWordAdapter(
    private val listWord : List<VocabularyWord>,
    private val context : Context,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<VocabularyWordAdapter.VocabularyWordViewHolder>() {

    inner class VocabularyWordViewHolder(val bind : ItemVocabularyWordBinding) : RecyclerView.ViewHolder(bind.root){

        fun binding(word : VocabularyWord) {
            Glide.with(context).load(word.image).into(bind.imgWord)
            bind.txtWord.text = word.word
            bind.txtMean.text = word.mean
            bind.txtExample.text = "Ex: What your name"
            bind.txtPronounce.text = word.pronounce
            bind.imgAdd.setOnClickListener {
                clickListener.onItemClick(listWord[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyWordViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = ItemVocabularyWordBinding.inflate(inflater, parent, false)
        return VocabularyWordViewHolder(view)
    }

    override fun getItemCount(): Int = listWord.size

    override fun onBindViewHolder(holder: VocabularyWordViewHolder, position: Int) {
        holder.binding(listWord[position])
    }
}