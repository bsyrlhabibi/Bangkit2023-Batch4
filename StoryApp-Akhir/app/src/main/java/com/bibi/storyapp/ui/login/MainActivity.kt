package com.bibi.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bibi.storyapp.R
import com.bibi.storyapp.data.remote.response.LoginRequest
import com.bibi.storyapp.databinding.ActivityMainBinding
import com.bibi.storyapp.ui.home.HomeActivity
import com.bibi.storyapp.ui.dataStore
import com.bibi.storyapp.ui.welcome.WelcomeActivity
import com.bibi.storyapp.viewmodel.LoginPreferences
import com.bibi.storyapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        playAnimation()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (!isDataOk()) {
                Toast.makeText(this, getString(R.string.incorrect_data), Toast.LENGTH_SHORT).show()
            } else {
                val account = LoginRequest(email, password)
                mainViewModel.loginAccount(account)
            }
        }

        mainViewModel.message.observe(this) {
            when(it) {
                "Unauthorized" -> {
                    Toast.makeText(this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show()
                }
                "success" -> {
                    val pref = LoginPreferences.getInstance(dataStore)
                    val loginViewModel = ViewModelProvider(this@MainActivity, ViewModelFactory(pref))[LoginViewModel::class.java]
                    mainViewModel.token.observe(this) { token ->
                        loginViewModel.setToken(token)
                        loginViewModel.setLoggedIn(true)
                        Toast.makeText(this, getString(R.string.success_login), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun playAnimation() {

        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditText =
            ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditText =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                emailTextView,
                emailEditText,
                passwordTextView,
                passwordEditText,
                login
            )
            startDelay = 500
        }.start()
    }

    private fun isDataOk() = binding.emailEditText.isValid && binding.passwordEditText.isValid

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}