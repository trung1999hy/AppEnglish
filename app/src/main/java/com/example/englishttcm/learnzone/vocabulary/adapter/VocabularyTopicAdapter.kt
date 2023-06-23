package com.example.englishttcm.learnzone.vocabulary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.learnzone.vocabulary.model.VocabularyTopic
import com.tpk.englishttcm.databinding.ItemVocabularyTopicBinding

class VocabularyTopicAdapter(
    private var listVocabTopic: List<VocabularyTopic>,
    private val itemClick: OnItemClickListener
) : RecyclerView.Adapter<VocabularyTopicAdapter.VocabTopicViewHolder>(), Filterable{

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
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<VocabularyTopic>()
                if (constraint.isNullOrBlank()) {
                    filteredList.addAll(listVocabTopic)
                } else {
                    listVocabTopic.forEach { item ->
                        if (item.name?.contains(constraint, true) == true) {
                            filteredList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listVocabTopic = results?.values as List<VocabularyTopic>
                notifyDataSetChanged()
            }
        }
    }


}
