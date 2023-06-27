package com.tpk.englishttcm.learnzone.listening.view


import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tpk.englishttcm.OnItemClickListener

import com.tpk.englishttcm.base.BaseFragment

import com.tpk.englishttcm.learnzone.listening.adapter.ListeningAdapter
import com.tpk.englishttcm.learnzone.listening.model.Listening
import com.tpk.englishttcm.learnzone.listening.viewmodel.ListeningViewModel
import com.tpk.englishttcm.databinding.FragmentListeningBinding


class ListeningFragment : BaseFragment<FragmentListeningBinding>() {
    private lateinit var viewModel: ListeningViewModel
    override fun getLayout(container: ViewGroup?): FragmentListeningBinding =
        FragmentListeningBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel = ViewModelProvider(this)[ListeningViewModel::class.java]




        viewModel.getAllListening().observe(viewLifecycleOwner) {
            binding.rcvListening.layoutManager = LinearLayoutManager(context)
            binding.rcvListening.adapter = ListeningAdapter(it, object : OnItemClickListener {
                override fun onItemClick(data: Any?) {
                    val listening = data as Listening
                    callback.showFragment(
                        ListeningFragment::class.java,
                        LearnListenFragment::class.java,
                        0,
                        0,
                        listening,
                        true
                    )
                }
            })
        }
        binding.btnBack.setOnClickListener {
            callback.backToPrevious()
        }

    }

}