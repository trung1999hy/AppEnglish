/* NAM NV created on 21:55 12-4-2023 */
package com.tpk.englishttcm.ui.activity

import android.content.Intent
import android.os.Looper
import com.tpk.englishttcm.base.BaseActivity
import android.os.Handler

import com.tpk.englishttcm.application.MyApplication
import com.tpk.englishttcm.databinding.ActivitySplashBinding

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