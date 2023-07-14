package com.bibi.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bibi.storyapp.ui.main.MainActivity
import com.bibi.storyapp.R
import com.bibi.storyapp.ViewModelFactory
import com.bibi.storyapp.data.model.Result
import com.bibi.storyapp.data.response.LoginResponse
import com.bibi.storyapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
        playAnimation()

        binding.loginButton.setOnClickListener {
            login()
        }

        val userViewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        loginViewModel =
            ViewModelProvider(this, userViewModelFactory)[LoginViewModel::class.java]

        loginViewModel.getToken().observe(this) { token ->
            if (token.isNotEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun initView() {
        binding.emailEditText.apply {
            hint = "Email"
        }

        binding.passwordEditText.apply {
            hint = "Password"
        }
    }

    private fun login() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        loginViewModel.login(email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success<*> -> {
                        binding.progressBar.visibility = View.GONE
                        val user = result.data as LoginResponse
                        if (user.error) {
                            Toast.makeText(
                                this@LoginActivity,
                                user.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            loginViewModel.save(
                                user.loginResult?.token ?: "",
                                user.loginResult?.name ?: ""
                            )
                            Toast.makeText(
                                this@LoginActivity,
                                getString(R.string.success_login),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            resources.getString(R.string.login_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
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
}

