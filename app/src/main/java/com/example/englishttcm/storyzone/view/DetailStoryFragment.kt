package com.example.englishttcm.storyzone.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.CustomDialogDownloadBinding
import com.example.englishttcm.databinding.FragmentDetailStoryBinding
import com.example.englishttcm.storyzone.callback.OnDownloadCompleteListener
import com.example.englishttcm.storyzone.model.Story
import com.example.englishttcm.storyzone.model.StoryDownloaded
import com.example.englishttcm.storyzone.viewmodel.StoryViewModel

class DetailStoryFragment : BaseFragment<FragmentDetailStoryBinding>() {
    private var storyViewModel: StoryViewModel? = null
    override fun getLayout(container: ViewGroup?): FragmentDetailStoryBinding =
        FragmentDetailStoryBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        storyViewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        val story = data as Story
        storyViewModel!!.loadImageFromFirebase(story.url, object : OnDownloadCompleteListener {
            override fun onDownloadComplete(data: Any?) {
                Glide.with(requireContext()).load(data as String)
                    .into(binding.imgStory)
                notify("Load success")
            }

            override fun onDownloadFailed(data: Any?) {
                notify("Load failed")
            }

        })
        binding.txtName.text = story.name
        binding.txtAuthor.text = story.author
        binding.txtDescription.text = story.describe
        binding.ivBack.setOnClickListener {
            callback.backToPrevious()
        }
        binding.btnDownload.setOnClickListener {
            storyViewModel!!.checkPermission(requireActivity())
        }
        storyViewModel!!.isPermissionGranted.observe(viewLifecycleOwner) {
            if (it) {
                val dialog = showDialog()
                dialog!!.show()
                storyViewModel!!.downloadFile(story, requireContext(), object : OnDownloadCompleteListener{
                    override fun onDownloadComplete(data: Any?) {
                        if(data as Boolean){
                            dialog.dismiss()
                            callback.showFragment(
                                DetailStoryFragment::class.java,
                                StoryFragment::class.java,
                                0,
                                0,
                                null,
                                true
                            )
                        }
                    }

                    override fun onDownloadFailed(data: Any?) {
                        if(data as Boolean){
                            notify("Download failed")
                        }
                    }

                })
            } else {
                notify("You need allow permission for download")
            }
        }
    }

    private fun showDialog(): AlertDialog? {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val binding = CustomDialogDownloadBinding.inflate(inflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        binding.btnCancel.setOnClickListener {
            storyViewModel!!.cancelDownload(data as Story, requireContext()).observe(viewLifecycleOwner){
                if(it){
                    dialog.dismiss()
                    notify("canceled")
                } else {
                    notify("cancel failed")
                }
            }
        }
        return dialog
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    storyViewModel?.updatePermission(true)
                } else {
                    storyViewModel?.updatePermission(false)
                }
            }
        }
    }

    companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 1
    }
}