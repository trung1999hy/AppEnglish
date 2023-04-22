package com.example.englishttcm.storyzone.view

import android.os.Build
import android.os.Environment
import android.view.ViewGroup
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentReadStoryBinding
import com.example.englishttcm.storyzone.model.StoryDownloaded
import java.io.File

class ReadStoryFragment : BaseFragment<FragmentReadStoryBinding>() {
    override fun getLayout(container: ViewGroup?): FragmentReadStoryBinding
        = FragmentReadStoryBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        val root = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        } else {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        }
        val story = data as StoryDownloaded
        val file = File(root, "books/${story.path}.pdf")
        binding.pdfView.fromFile(file)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onLoad { }
            .spacing(4)
            .onPageChange { _, _ -> }
            .onPageScroll { i: Int, fl: Float -> }
            .load()
    }

}