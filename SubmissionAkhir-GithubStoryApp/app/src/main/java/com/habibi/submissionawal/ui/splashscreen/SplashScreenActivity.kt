package com.habibi.submissionawal.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.habibi.submissionawal.R
import com.habibi.submissionawal.ui.activity.MainActivity


class SplashScreenActivity : AppCompatActivity() {
    private val Splash:Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        android.os.Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, Splash)
    }
}