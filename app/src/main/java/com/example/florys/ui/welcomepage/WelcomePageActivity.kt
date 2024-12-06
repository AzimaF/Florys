package com.example.florys.ui.welcomepage

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.example.florys.R.id.welcomeregisterButton
import com.example.florys.databinding.ActivityWelcomePageBinding
import com.example.florys.ui.login.LoginActivity
import com.example.florys.ui.register.RegisterActivity

class WelcomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.welcomeloginButton.setOnClickListener {
            startActivity(Intent(this@WelcomePageActivity, LoginActivity::class.java))
        }
        binding.welcomeregisterButton.setOnClickListener {
            startActivity(Intent(this@WelcomePageActivity, RegisterActivity::class.java))
        }
    }
}