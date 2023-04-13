/* NAM NV created on 21:50 12-4-2023 */
package com.example.englishttcm.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.englishttcm.R
import com.example.englishttcm.base.BaseActivity
import com.example.englishttcm.databinding.ActivityMainBinding
import com.example.englishttcm.view.fragment.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>(){
    override fun initViews() {
        showFragment(this::class.java, HomeFragment::class.java)
    }

    override fun getLayout(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}