package com.bibi.storyapp.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bibi.storyapp.R
import com.bibi.storyapp.adapter.LoadingStateAdapter
import com.bibi.storyapp.adapter.StoryAdapter
import com.bibi.storyapp.databinding.ActivityHomeBinding
import com.bibi.storyapp.ui.maps.MapsActivity
import com.bibi.storyapp.ui.add.UploadActivity
import com.bibi.storyapp.ui.dataStore
import com.bibi.storyapp.ui.login.LoginViewModel
import com.bibi.storyapp.ui.login.MainActivity
import com.bibi.storyapp.viewmodel.*

@OptIn(ExperimentalPagingApi::class)
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactoryPaging(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(this@HomeActivity, "Bye!", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvStories.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvStories.layoutManager = LinearLayoutManager(this)
        }

        val pref = LoginPreferences.getInstance(dataStore)
        val loginViewModel = ViewModelProvider(this@HomeActivity, ViewModelFactory(pref))[LoginViewModel::class.java]

        loginViewModel.getToken().observe(this) {
            setStory(it)
        }

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this@HomeActivity, UploadActivity::class.java))
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                val pref = LoginPreferences.getInstance(dataStore)
                val loginViewModel = ViewModelProvider(this@HomeActivity, ViewModelFactory(pref))[LoginViewModel::class.java]
                loginViewModel.setLoggedIn(false)
                loginViewModel.setToken("")
                startActivity(Intent(this@HomeActivity, MainActivity::class.java))
                finish()
            }

            R.id.action_refresh -> {
                val pref = LoginPreferences.getInstance(dataStore)
                val loginViewModel = ViewModelProvider(this@HomeActivity, ViewModelFactory(pref))[LoginViewModel::class.java]
                loginViewModel.getToken().observe(this) {
                    setStory(it)
                }
            }

            R.id.action_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))

            R.id.action_maps -> startActivity(Intent(this@HomeActivity, MapsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    @ExperimentalPagingApi
    private fun setStory(token: String) {
        val adapter = StoryAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        homeViewModel.getAllStory(token).observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}