package com.example.englishttcm.learnzone.vocabulary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord
import com.example.englishttcm.learnzone.vocabulary.OnItemWordClickListener
import com.tpk.englishttcm.databinding.ItemVocabularyWordBinding

class VocabularyWordAdapter(
    private val listWord : List<VocabularyWord>,
    private val clickListener: OnItemWordClickListener
) : RecyclerView.Adapter<VocabularyWordAdapter.VocabularyWordViewHolder>() {
    inner class VocabularyWordViewHolder(private val bind : ItemVocabularyWordBinding) : RecyclerView.ViewHolder(bind.root) {
        fun binding(word : VocabularyWord) {
            Glide.with(itemView.context).load(word.image).into(bind.imgWord)
            bind.txtWord.text = word.word
            bind.txtMean.text = word.mean
            bind.txtExample.text = word.example
            bind.txtPronounce.text = word.pronounce

        }
        init {
            bind.speaker.setOnClickListener {
                clickListener.onItemClick(listWord[adapterPosition])
            }

            bind.btnNote.setOnClickListener {
                clickListener.onItemNoteClick(listWord[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyWordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemVocabularyWordBinding.inflate(inflater, parent, false)
        return VocabularyWordViewHolder(view)
    }

    override fun getItemCount(): Int = listWord.size

    override fun onBindViewHolder(holder: VocabularyWordViewHolder, position: Int) {
        holder.binding(listWord[position])
    }
}