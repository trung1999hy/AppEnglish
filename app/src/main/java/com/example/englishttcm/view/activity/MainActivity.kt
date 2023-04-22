/* NAM NV created on 21:50 12-4-2023 */
package com.example.englishttcm.view.activity

import com.example.englishttcm.R
import com.example.englishttcm.base.BaseActivity
import com.example.englishttcm.databinding.ActivityMainBinding
import com.example.englishttcm.learnzone.log.view.LogInFragment
import com.example.englishttcm.translate.view.TranslateFragment

class MainActivity : BaseActivity<ActivityMainBinding>(){
    override fun initViews() {
        showFragment(this::class.java, LogInFragment::class.java, R.anim.slide_in, 0)
        binding.fabTranslate.setOnClickListener {

            val fragment = TranslateFragment()
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fr_main,fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
//            showFragment(this::class.java,TranslateFragment::class.java,0,0,null,true)
        }
    }

    override fun getLayout(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}