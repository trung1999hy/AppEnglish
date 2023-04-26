package com.example.englishttcm.playzone.scramble.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishttcm.databinding.ItemScrambleBinding
import com.example.englishttcm.playzone.scramble.model.Scramble

class ScrambleAdapter(private val listScramble: List<Scramble>): RecyclerView.Adapter<ScrambleAdapter.ScrambleViewHolder>() {

    inner class ScrambleViewHolder(private val binding: ItemScrambleBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(scramble: Scramble) {
            binding.tvScrambleWord.text = scramble.scrambled
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrambleViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = ItemScrambleBinding.inflate(inflate, parent, false)
        return ScrambleViewHolder(view)
    }

    override fun getItemCount(): Int = listScramble.size

    override fun onBindViewHolder(holder: ScrambleViewHolder, position: Int) {
        holder.bind(listScramble[position])
    }
}