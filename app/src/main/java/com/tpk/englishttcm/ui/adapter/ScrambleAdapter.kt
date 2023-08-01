package com.tpk.englishttcm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tpk.englishttcm.data.remote.firebase.model.Scramble
import com.tpk.englishttcm.databinding.ItemScrambleBinding

class ScrambleAdapter(private val listScramble: List<Scramble>) :
    RecyclerView.Adapter<ScrambleAdapter.ScrambleViewHolder>() {

    inner class ScrambleViewHolder(private val binding: ItemScrambleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scramble: Scramble) {
            binding.tvScrambleWord.text = scramble.scrambled
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrambleViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = ItemScrambleBinding.inflate(inflate, parent, false)
        return ScrambleViewHolder(view)
    }

    fun getItem(position: Int) = listScramble[position]

    override fun getItemCount(): Int = listScramble.size

    override fun onBindViewHolder(holder: ScrambleViewHolder, position: Int) {
        holder.bind(listScramble[position])
    }
}