package com.tpk.englishttcm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tpk.englishttcm.callback.OnItemClickListener
import com.tpk.englishttcm.callback.OnDownloadCompleteListener
import com.tpk.englishttcm.data.remote.firebase.model.Story
import com.tpk.englishttcm.viewmodel.StoryViewModel
import com.tpk.englishttcm.databinding.ItemStoryBinding

class StoryRowAdapter(
    private val listStory: List<Story>,
    private val storyViewModel: StoryViewModel,
    private val onItemClick: OnItemClickListener
) : RecyclerView.Adapter<StoryRowAdapter.StoryRowViewHolder>() {

    inner class StoryRowViewHolder(val bind: ItemStoryBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun binding(book: Story) {
            storyViewModel.loadImageFromFirebase(book.url, object : OnDownloadCompleteListener {
                override fun onDownloadComplete(data: Any?) {
                    Glide.with(itemView.context).load(data as String)
                        .into(bind.ivStory)
                }

                override fun onDownloadFailed(data: Any?) {

                }
            })
            bind.tvStory.text = book.name
            bind.ivStory.setOnClickListener {
                onItemClick.onItemClick(book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryRowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemStoryBinding.inflate(inflater, parent, false)
        return StoryRowViewHolder(view)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: StoryRowViewHolder, position: Int) {
        holder.binding(listStory[position])
    }

}