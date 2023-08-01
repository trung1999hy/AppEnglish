package com.tpk.englishttcm.ui.fragment.book

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tpk.englishttcm.callback.OnItemClickListener
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.ui.adapter.StoryAdapter
import com.tpk.englishttcm.ui.adapter.StoryDownloadedAdapter
import com.tpk.englishttcm.data.local.entity.StoryDownloaded
import com.tpk.englishttcm.viewmodel.StoryViewModel
import com.tpk.englishttcm.databinding.CustomDialogDeleteBinding
import com.tpk.englishttcm.databinding.FragmentStoryBinding

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
            dialog.cancel()
        }
        binding.btnNo.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }
        dialog.show()
    }
}