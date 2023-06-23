package com.example.englishttcm.storyzone.view

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.storyzone.model.StoryDownloaded
import com.example.englishttcm.storyzone.viewmodel.StoryViewModel
import com.tpk.englishttcm.databinding.FragmentReadStoryBinding

class ReadStoryFragment : BaseFragment<FragmentReadStoryBinding>() {
    private var storyViewModel: StoryViewModel? = null
    override fun getLayout(container: ViewGroup?): FragmentReadStoryBinding =
        FragmentReadStoryBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        val story = data as StoryDownloaded
        storyViewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        storyViewModel!!.getPdfFromLocal(story.path, requireContext()).observe(viewLifecycleOwner) {
            binding.pdfView.fromFile(it)
                .defaultPage(story.currentPage)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .onLoad { }
                .spacing(7)
                .onPageChange { _, _ -> }
                .onPageScroll { _: Int, _: Float -> }
                .load()
        }
        binding.ivBack.setOnClickListener {
            callback.backToPrevious()
        }
    }

    private fun saveCurrentPage(){
        val currentPagePdf = binding.pdfView.currentPage
        val story = data as StoryDownloaded
        story.currentPage = currentPagePdf
        storyViewModel!!.updateStoryDownload(story, requireContext())
        storyViewModel!!.updateResult.observe(viewLifecycleOwner) {
            if (it) {
                notify("Update current page success")
            } else {
                notify("Update current page failed")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveCurrentPage()
    }
}