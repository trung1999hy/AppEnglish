package com.example.englishttcm.storyzone.view

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentStoryBinding
import com.example.englishttcm.storyzone.adapter.StoryAdapter
import com.example.englishttcm.storyzone.viewmodel.StoryViewModel

class StoryFragment : BaseFragment<FragmentStoryBinding>(){
    private lateinit var storyViewModel : StoryViewModel
    override fun getLayout(container: ViewGroup?): FragmentStoryBinding = FragmentStoryBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        storyViewModel =  ViewModelProvider(this)[StoryViewModel::class.java]
        storyViewModel.getListGenreLive.observe(viewLifecycleOwner){
            binding.rcvAllStory.adapter = StoryAdapter(it, requireContext(),storyViewModel, viewLifecycleOwner, this)
        }
    }
}