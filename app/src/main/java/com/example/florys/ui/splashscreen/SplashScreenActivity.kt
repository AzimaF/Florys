package com.example.florys.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.florys.databinding.ActivitySplashScreenBinding
import com.example.florys.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupViewModel()

        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Hide ActionBar
        supportActionBar?.hide()

        val splashTimeOut = 3000

        Thread {
            try {
                Thread.sleep(splashTimeOut.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

}