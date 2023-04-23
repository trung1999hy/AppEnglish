package com.example.englishttcm.storyzone.view

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.OnItemClickListener
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.CustomDialogDeleteBinding
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
                    }, object : OnItemClickListener {
                        override fun onItemClick(data: Any?) {
                            val story = data as StoryDownloaded
                            showDialog(story)
                        }
                    })
            }
        }
        storyViewModel.getListGenreLive.observe(viewLifecycleOwner) {
            binding.rcvAllStory.adapter =
                StoryAdapter(it, storyViewModel, viewLifecycleOwner, this.callback)
        }
        binding.ivBack.setOnClickListener {
            callback.backToPrevious()
        }
    }

    private fun showDialog(storyDownloaded: StoryDownloaded) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val binding = CustomDialogDeleteBinding.inflate(inflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        binding.btnYes.setOnClickListener {
            storyViewModel.deleteStoryDownloaded(storyDownloaded, requireContext())
            storyViewModel.deleteResult.observe(viewLifecycleOwner) {
                if (it) {
                    notify("Delete story downloaded success")
                } else {
                    notify("Delete story downloaded failed")
                }
            }
            storyViewModel.deleteFileLocal(storyDownloaded, requireContext())
            storyViewModel.deleteLocalResult.observe(viewLifecycleOwner) {
                if (it) {
                    notify("Delete file local success")
                } else {
                    notify("Delete file local failed")
                }
            }
            dialog.dismiss()
        }
        binding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}