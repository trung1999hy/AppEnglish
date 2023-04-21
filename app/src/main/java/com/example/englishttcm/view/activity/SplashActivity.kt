/* NAM NV created on 21:55 12-4-2023 */
package com.example.englishttcm.view.activity

import android.content.Intent
import android.os.Looper
import com.example.englishttcm.base.BaseActivity
import com.example.englishttcm.databinding.ActivitySplashBinding
import android.os.Handler
import com.example.englishttcm.application.AdsApplication

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun initViews() {
        val application = application as AdsApplication
        Handler(Looper.myLooper()!!).postDelayed({
            application.showAdIfAvailable(
                this,
                object : AdsApplication.OnShowAdCompleteListener {
                    override fun onShowAdComplete() {
                        startMainActivity()
                        finish()
                    }
                })
        }, 2000)

    }

    override fun getLayout(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)

    fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}