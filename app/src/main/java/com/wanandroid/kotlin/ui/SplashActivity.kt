package com.wanandroid.kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.wanandroid.kotlin.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_splash)

        AnimationUtils.loadAnimation(this, R.anim.scale).also { hyperspaceJumpAnimation ->
            splashLogoIv.startAnimation(hyperspaceJumpAnimation)
        }

        Handler(Looper.getMainLooper()).postDelayed(
            kotlin.run { Runnable { goToMainActivity() } },
            1200
        )
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}