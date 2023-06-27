package com.tpk.englishttcm.learnzone.listening.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tpk.englishttcm.OnItemClickListener
import com.tpk.englishttcm.learnzone.listening.model.Listening
import com.tpk.englishttcm.databinding.ItemListeningBinding


class ListeningAdapter(
    private val listListening: List<Listening>,
    private val itemClick: OnItemClickListener
) : RecyclerView.Adapter<ListeningAdapter.ListeningViewHolder>() {

    inner class ListeningViewHolder(private val binding: ItemListeningBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listening: Listening) {
            binding.tvTitle.text = listening.title
            Glide.with(itemView.context).load(listening.image).into(binding.imgListening)
        }
        init {
            binding.rlListening.setOnClickListener {
                itemClick.onItemClick(listListening[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeningViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = ItemListeningBinding.inflate(inflate, parent, false)
        return ListeningViewHolder(view)
    }

    override fun getItemCount(): Int{
        return listListening.size
    }

    override fun onBindViewHolder(holder: ListeningViewHolder, position: Int) {
        holder.bind(listListening[position])
    }
}