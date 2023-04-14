/* NAM NV created on 21:50 12-4-2023 */
package com.example.englishttcm.view.activity

import com.example.englishttcm.base.BaseActivity
import com.example.englishttcm.databinding.ActivityMainBinding
import com.example.englishttcm.log.view.LogFragment

class MainActivity : BaseActivity<ActivityMainBinding>(){
    override fun initViews() {
        showFragment(this::class.java, LogFragment::class.java)
    }

    override fun getLayout(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}