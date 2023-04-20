package com.example.englishttcm.storyzone.view

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentDetailStoryBinding
import com.example.englishttcm.storyzone.model.Story

class DetailStoryFragment : BaseFragment<FragmentDetailStoryBinding>() {
    override fun getLayout(container: ViewGroup?): FragmentDetailStoryBinding
        = FragmentDetailStoryBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        val story = data as Story
        Glide.with(requireContext()).load(story.image).into(binding.imgStory)
        binding.txtName.text = story.name
        binding.txtAuthor.text = story.author
        binding.txtDescription.text = story.describe
    }
}