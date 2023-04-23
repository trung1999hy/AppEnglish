package com.example.englishttcm.storyzone.view

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentReadStoryBinding
import com.example.englishttcm.storyzone.model.StoryDownloaded
import com.example.englishttcm.storyzone.util.Util
import com.example.englishttcm.storyzone.viewmodel.StoryViewModel
import java.io.File

class ReadStoryFragment : BaseFragment<FragmentReadStoryBinding>() {
    private var storyViewModel: StoryViewModel? = null
    override fun getLayout(container: ViewGroup?): FragmentReadStoryBinding =
        FragmentReadStoryBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        val root = Util.getRoot(requireContext())
        val story = data as StoryDownloaded
        val file = File(root, "books/${story.path}.pdf")
        storyViewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        storyViewModel!!.getPdfFromLocal(story.path, requireContext()).observe(viewLifecycleOwner) {
            binding.pdfView.fromFile(file)
                .defaultPage(story.currentPage)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .onLoad { }
                .spacing(4)
                .onPageChange { _, _ -> }
                .onPageScroll { _: Int, _: Float -> }
                .load()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
}