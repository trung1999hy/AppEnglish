/* NAM NV created on 21:55 12-4-2023 */
package com.example.englishttcm.view.activity

import android.content.Intent
import android.os.Looper
import com.example.englishttcm.base.BaseActivity
import com.example.englishttcm.databinding.ActivitySplashBinding
import android.os.Handler

import com.example.englishttcm.application.MyApplication

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun initViews() {
        val application = application as MyApplication
        Handler(Looper.myLooper()!!).postDelayed({
            startMainActivity()
            finish()
        }, 2000)

    }

    override fun getLayout(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}