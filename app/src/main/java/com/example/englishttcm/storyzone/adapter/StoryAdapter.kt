package com.example.englishttcm.storyzone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.englishttcm.OnActionCallback
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.databinding.ItemRowStoryBinding
import com.example.englishttcm.storyzone.model.Genre
import com.example.englishttcm.storyzone.model.Story
import com.example.englishttcm.storyzone.view.DetailStoryFragment
import com.example.englishttcm.storyzone.view.ReadStoryFragment
import com.example.englishttcm.storyzone.view.StoryFragment
import com.example.englishttcm.storyzone.viewmodel.StoryViewModel

class StoryAdapter(
    private val listGenres: List<Genre>,
    private val storyViewModel: StoryViewModel,
    private val viewLifeCycle: LifecycleOwner,
    private val onActionCallback: OnActionCallback,
) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    inner class StoryViewHolder(private val bind: ItemRowStoryBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun binding(genre: Genre) {
            bind.txtGenre.text = genre.name
            storyViewModel.getListStoryLive(genre.id).observe(viewLifeCycle) {
                bind.rcvRowStory.adapter = StoryRowAdapter(
                    it,
                    storyViewModel,
                    viewLifeCycle,
                    object : OnItemClickListener {
                        override fun onItemClick(data: Any?) {
                            val story = data as Story
                            storyViewModel.checkIsDownloadStory(story.id, itemView.context)
                                .observe(viewLifeCycle) {
                                    if (it != null) {
                                        onActionCallback.showFragment(
                                            StoryFragment::class.java,
                                            ReadStoryFragment::class.java,
                                            0,
                                            0,
                                            it,
                                            true
                                        )
                                    } else {
                                        onActionCallback.showFragment(
                                            StoryFragment::class.java,
                                            DetailStoryFragment::class.java,
                                            0,
                                            0,
                                            story,
                                            true
                                        )
                                    }
                                }
                        }
                    })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemRowStoryBinding.inflate(inflater, parent, false)
        return StoryViewHolder(view)
    }

    override fun getItemCount(): Int = listGenres.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.binding(listGenres[position])
    }
}