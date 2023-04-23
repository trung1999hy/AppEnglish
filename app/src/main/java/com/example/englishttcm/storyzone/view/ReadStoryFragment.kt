package com.example.englishttcm.storyzone.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentReadStoryBinding
import com.example.englishttcm.db.EnglishDatabase
import com.example.englishttcm.storyzone.model.StoryDownloaded
import com.example.englishttcm.storyzone.util.Util
import com.example.englishttcm.storyzone.viewmodel.StoryViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            if (mContext != null) {
                val currentPagePdf = binding.pdfView.currentPage
                val story = data as StoryDownloaded
                story.currentPage = currentPagePdf
                EnglishDatabase.getDatabase(requireContext()).getEnglishDao()
                    .updateStoryDownloaded(story)
                notify("Update current page success")
            } else {
                notify("Context null")
            }
        }
    }
}