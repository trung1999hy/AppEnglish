package com.tpk.englishttcm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.tpk.englishttcm.callback.OnActionCallback
import com.tpk.englishttcm.callback.OnItemClickListener

import com.tpk.englishttcm.data.remote.firebase.model.Genre
import com.tpk.englishttcm.data.remote.firebase.model.Story
import com.tpk.englishttcm.ui.fragment.book.DetailStoryFragment
import com.tpk.englishttcm.ui.fragment.book.ReadStoryFragment
import com.tpk.englishttcm.ui.fragment.book.StoryFragment
import com.tpk.englishttcm.viewmodel.StoryViewModel
import com.tpk.englishttcm.databinding.ItemRowStoryBinding

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
                    object : OnItemClickListener {
                        override fun onItemClick(data: Any?) {
                            val story = data as Story
                            storyViewModel.getStoryDownloadById(story.id, itemView.context)
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