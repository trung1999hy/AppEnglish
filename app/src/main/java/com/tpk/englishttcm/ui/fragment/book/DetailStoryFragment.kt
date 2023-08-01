package com.tpk.englishttcm.ui.fragment.book

import android.Manifest
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tpk.englishttcm.application.MyApplication
import com.tpk.englishttcm.base.BaseFragment
import com.tpk.englishttcm.callback.OnDownloadCompleteListener
import com.tpk.englishttcm.data.remote.firebase.model.Story
import com.tpk.englishttcm.data.local.entity.StoryDownloaded
import com.tpk.englishttcm.viewmodel.StoryViewModel
import com.tpk.englishttcm.databinding.CustomDialogDownloadBinding
import com.tpk.englishttcm.databinding.FragmentDetailStoryBinding

class DetailStoryFragment : BaseFragment<FragmentDetailStoryBinding>() {
    private var storyViewModel: StoryViewModel? = null
    private var currentCoin = MyApplication.getInstance().getPreference().getValueCoin()
    override fun getLayout(container: ViewGroup?): FragmentDetailStoryBinding =
        FragmentDetailStoryBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        storyViewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        val story = data as Story
        storyViewModel!!.loadImageFromFirebase(story.url, object : OnDownloadCompleteListener {
            override fun onDownloadComplete(data: Any?) {
                Glide.with(requireContext()).load(data as String)
                    .into(binding.imgStory)
            }

            override fun onDownloadFailed(data: Any?) {

            }

        })
        binding.txtName.text = story.name
        binding.txtAuthor.text = story.author
        binding.txtDescription.text = story.describe
        binding.ivBack.setOnClickListener {
            callback.backToPrevious()
        }
        binding.btnDownload.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.apply {
                setTitle("Purchase confirmation")
                setMessage("Are you sure to pay 2 gold to download this story?")
                setPositiveButton(
                    "Yes"
                ) { dialogInterface, which ->
                    if (currentCoin >= 2) {
                        currentCoin -= 2
                        MyApplication.getInstance().getPreference().setValueCoin(currentCoin)
                        storyViewModel!!.checkPermission(requireActivity())
                    } else {
                        Toast.makeText(
                            context,
                            "You do not have enough coin, please top up to use this feature",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                setNegativeButton(
                    "No"
                ) { dialog, which ->
                    dialog.dismiss()
                }
            }

            val dialog = alertDialog.create()
            dialog.show()
        }
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permission ->
            if (permission.all { it.value }) {
                downloadFile(story)
            } else {
                notify("You need allow permission for download")
            }
        }
        storyViewModel!!.isPermissionGranted.observe(viewLifecycleOwner) {
            if (it) {
                downloadFile(story)
            } else {
                val permissions = arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                requestPermissionLauncher.launch(permissions)
            }
        }
    }

    private fun downloadFile(story: Story) {
        val dialog = showDialog()
        dialog!!.show()
        storyViewModel!!.downloadFile(
            story,
            requireContext(),
            object : OnDownloadCompleteListener {
                override fun onDownloadComplete(data: Any?) {
                    val storyDownloaded =
                        StoryDownloaded(story.id, story.name, story.url, 0)
                    storyViewModel!!.insertStoryDownload(
                        storyDownloaded,
                        requireContext()
                    )
                    storyViewModel!!.insertResult.observe(viewLifecycleOwner) {
                        if (it) {
                            notify("Insert success")
                        } else {
                            notify("Insert failed")
                        }
                    }
                    dialog.dismiss()
                    dialog.cancel()
                    callback.backToPrevious()
                }

                override fun onDownloadFailed(data: Any?) {
                    notify("Download failed")
                }
            })
    }

    private fun showDialog(): AlertDialog? {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val binding = CustomDialogDownloadBinding.inflate(inflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        binding.btnCancel.setOnClickListener {
            val story = data as Story
            val storyDownloaded = StoryDownloaded(story.id, story.name, story.url, 0)
            storyViewModel!!.cancelDownload()
            storyViewModel!!.cancelResult.observe(viewLifecycleOwner) {
                if (it) {
                    dialog.dismiss()
                    dialog.cancel()
                    notify("canceled")
                } else {
                    notify("cancel failed")
                }
            }
            storyViewModel!!.deleteFileLocal(storyDownloaded, requireContext())
            storyViewModel!!.deleteLocalResult.observe(viewLifecycleOwner) {
                if (it) {
                    notify("Delete file local success")
                } else {
                    notify("Delete file local failed")
                }
            }
            storyViewModel!!.deleteStoryDownloaded(storyDownloaded, requireContext())
            storyViewModel!!.deleteResult.observe(viewLifecycleOwner) {
                if (it) {
                    notify("Delete story downloaded success")
                } else {
                    notify("Delete story downloaded local failed")
                }
            }
        }
        return dialog
    }
}