package com.example.englishttcm.storyzone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.databinding.ItemStoryBinding
import com.example.englishttcm.storyzone.model.Story

class StoryRowAdapter(
    private val listStory: List<Story>,
    private val context: Context,
    private val onItemClick : OnItemClickListener
) : RecyclerView.Adapter<StoryRowAdapter.StoryRowViewHolder>() {

    inner class StoryRowViewHolder(val bind : ItemStoryBinding) : RecyclerView.ViewHolder(bind.root){
        fun binding(book : Story){
            Glide.with(context).load(book.image).into(bind.ivStory)
            bind.tvStory.text = book.name
            bind.ivStory.setOnClickListener {
                onItemClick.onItemClick(book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryRowViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = ItemStoryBinding.inflate(inflater, parent, false)
        return StoryRowViewHolder(view)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: StoryRowViewHolder, position: Int) {
        holder.binding(listStory[position])
    }

}