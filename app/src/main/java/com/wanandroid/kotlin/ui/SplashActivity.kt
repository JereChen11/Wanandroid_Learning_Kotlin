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
import com.wanandroid.kotlin.databinding.ActivitySplashBinding
import com.wanandroid.kotlin.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AnimationUtils.loadAnimation(this, R.anim.scale).also { hyperspaceJumpAnimation ->
            binding.splashLogoIv.startAnimation(hyperspaceJumpAnimation)
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