/* NAM NV created on 21:50 12-4-2023 */
package com.tpk.englishttcm.ui.activity

import com.tpk.englishttcm.base.BaseActivity
import com.tpk.englishttcm.ui.fragment.home.HomeFragment
import com.tpk.englishttcm.R
import com.tpk.englishttcm.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(){

    override fun initViews() {
        showFragment(this::class.java, HomeFragment::class.java, R.anim.slide_in, 0)
    }

    override fun getLayout(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}