package com.tpk.englishttcm.storyzone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.tpk.englishttcm.OnItemClickListener
import com.tpk.englishttcm.storyzone.model.StoryDownloaded
import com.tpk.englishttcm.storyzone.viewmodel.StoryViewModel
import com.tpk.englishttcm.databinding.ItemStoryBinding

class StoryDownloadedAdapter(
    private val listStory: List<StoryDownloaded>,
    private val storyViewModel: StoryViewModel,
    private val viewLifecycle: LifecycleOwner,
    private val onItemClick: OnItemClickListener,
    private val onItemDelete: OnItemClickListener
) : RecyclerView.Adapter<StoryDownloadedAdapter.StoryDownloadedViewHolder>() {

    inner class StoryDownloadedViewHolder(private val bind: ItemStoryBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun binding(storyDownloaded: StoryDownloaded) {
            storyViewModel.loadImageFromLocal(storyDownloaded.path, bind.root.context)
                .observe(viewLifecycle) {
                    bind.ivStory.setImageBitmap(it)
                }
            bind.tvStory.text = storyDownloaded.name
            bind.ivStory.setOnClickListener {
                onItemClick.onItemClick(storyDownloaded)
            }
            bind.imgDelete.visibility = View.VISIBLE
            bind.imgDelete.setOnClickListener {
                onItemDelete.onItemClick(storyDownloaded)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryDownloadedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemStoryBinding.inflate(inflater, parent, false)
        return StoryDownloadedViewHolder(view)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: StoryDownloadedViewHolder, position: Int) {
        holder.binding(listStory[position])
    }

}