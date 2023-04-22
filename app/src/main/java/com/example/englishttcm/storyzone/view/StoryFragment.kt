package com.example.englishttcm.storyzone.view

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.OnActionCallback
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentStoryBinding
import com.example.englishttcm.storyzone.adapter.StoryAdapter
import com.example.englishttcm.storyzone.adapter.StoryDownloadedAdapter
import com.example.englishttcm.storyzone.model.StoryDownloaded
import com.example.englishttcm.storyzone.viewmodel.StoryViewModel

class StoryFragment : BaseFragment<FragmentStoryBinding>() {
    private lateinit var storyViewModel: StoryViewModel
    override fun getLayout(container: ViewGroup?): FragmentStoryBinding =
        FragmentStoryBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        storyViewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        storyViewModel.getListStoryDownloadedLive.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rcvMyLibary.adapter = StoryDownloadedAdapter(
                    it,
                    storyViewModel,
                    viewLifecycleOwner,
                    object : OnItemClickListener {
                        override fun onItemClick(data: Any?) {
                            callback.showFragment(
                                StoryFragment::class.java,
                                ReadStoryFragment::class.java,
                                0,
                                0,
                                data as StoryDownloaded,
                                true
                            )
                        }
                    })
            }
        }
        storyViewModel.getListGenreLive.observe(viewLifecycleOwner) {
            binding.rcvAllStory.adapter =
                StoryAdapter(it, storyViewModel, viewLifecycleOwner, this.callback)
        }
    }
}