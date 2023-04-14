package com.example.englishttcm.log.view

import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.englishttcm.base.BaseFragment
import com.example.englishttcm.databinding.FragmentLogBinding
import com.example.englishttcm.log.adapter.LogPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class LogFragment : BaseFragment<FragmentLogBinding>(){

    override fun getLayout(container: ViewGroup?): FragmentLogBinding =
        FragmentLogBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        binding.tabLog.addTab(binding.tabLog.newTab().setText("Log in"))
        binding.tabLog.addTab(binding.tabLog.newTab().setText("Sign up"))

        binding.vpgLog.adapter = LogPagerAdapter(requireActivity().supportFragmentManager, lifecycle)

        binding.tabLog.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.vpgLog.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // do nothing
            }
        })

        binding.vpgLog.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLog.selectTab(binding.tabLog.getTabAt(position))
            }
        })

    }
}