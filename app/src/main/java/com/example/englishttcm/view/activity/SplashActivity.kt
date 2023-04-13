/* NAM NV created on 21:55 12-4-2023 */
package com.example.englishttcm.view.activity

import android.content.Intent
import android.os.Looper
import com.example.englishttcm.base.BaseActivity
import com.example.englishttcm.databinding.ActivitySplashBinding
import android.os.Handler

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun initViews() {
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }

    override fun getLayout(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
}