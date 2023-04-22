package com.example.englishttcm.storyzone.view

import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentDetailStoryBinding
import com.example.englishttcm.storyzone.callback.OnDownloadCompleteListener
import com.example.englishttcm.storyzone.model.Story
import com.example.englishttcm.storyzone.viewmodel.StoryViewModel

class DetailStoryFragment : BaseFragment<FragmentDetailStoryBinding>() {
    private var storyViewModel: StoryViewModel? = null
    override fun getLayout(container: ViewGroup?): FragmentDetailStoryBinding =
        FragmentDetailStoryBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        storyViewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        val story = data as Story
        storyViewModel!!.loadImageFromFirebase(story.url, object :OnDownloadCompleteListener{
            override fun onDownloadComplete(downloadUrl: String) {
                Glide.with(requireContext()).load(downloadUrl)
                    .into(binding.imgStory)
                notify("Load success")
            }

            override fun onDownloadFailed(errorMessage: String?) {
                notify("Load failed")
            }

        })
        binding.txtName.text = story.name
        binding.txtAuthor.text = story.author
        binding.txtDescription.text = story.describe
        binding.btnDownload.setOnClickListener {
            storyViewModel!!.checkPermission(requireActivity(), story, requireContext())
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val story = data as Story
                storyViewModel!!.downloadFile(story, requireContext())
            } else {
                notify("You need allow permission for download file")
            }
        }
    }

    companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 1
    }
}