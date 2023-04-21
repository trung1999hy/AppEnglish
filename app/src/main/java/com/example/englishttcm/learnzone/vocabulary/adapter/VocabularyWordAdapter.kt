package com.example.englishttcm.learnzone.vocabulary.adapter

import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.databinding.ItemVocabularyWordBinding
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyWord

class VocabularyWordAdapter(
    private val listVocabWord: List<VocabularyWord>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<VocabularyWordAdapter.VocabularyWordViewHolder>() {

    inner class VocabularyWordViewHolder(val bind : ItemVocabularyWordBinding) : RecyclerView.ViewHolder(bind.root){

        fun binding(vocabWord : VocabularyWord) {
            Glide.with(itemView.context).load(vocabWord.image).into(bind.imgWord)
            bind.txtWord.text = vocabWord.word
            bind.txtMean.text = vocabWord.mean
            bind.txtExample.text = vocabWord.example
            bind.txtPronounce.text = vocabWord.pronounce
        }
        init {
            bind.btnSpeaker.setOnClickListener {
                clickListener.onItemClick(listVocabWord[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyWordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemVocabularyWordBinding.inflate(inflater, parent, false)
        return VocabularyWordViewHolder(view)
    }

    override fun getItemCount(): Int = listVocabWord.size

    override fun onBindViewHolder(holder: VocabularyWordViewHolder, position: Int) {
        holder.binding(listVocabWord[position])
    }
}