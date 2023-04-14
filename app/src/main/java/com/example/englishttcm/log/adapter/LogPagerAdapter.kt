package com.example.englishttcm.log.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.englishttcm.log.view.LogInFragment
import com.example.englishttcm.log.view.SignUpFragment

class LogPagerAdapter(
    fragmentManager: FragmentManager,
    viewLifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, viewLifecycle){
    override fun getItemCount(): Int = 2
    //test
    //hello

    override fun createFragment(position: Int): Fragment {
        return if(position == 0) LogInFragment() else SignUpFragment()
    }

}