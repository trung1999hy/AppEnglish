/* NAM NV created on 21:50 12-4-2023 */
package com.example.englishttcm.view.activity

import com.example.englishttcm.base.BaseActivity
import com.example.englishttcm.home.HomeFragment
import com.tpk.englishttcm.R
import com.tpk.englishttcm.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(){

    override fun initViews() {
        showFragment(this::class.java, HomeFragment::class.java, R.anim.slide_in, 0)
    }

    override fun getLayout(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}