package com.habibi.submissionawal.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.habibi.submissionawal.R
import com.habibi.submissionawal.adapter.SectionsPagerAdapter
import com.habibi.submissionawal.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val USERNAME = "username"
        const val ID = "id"
        const val AVATAR = "avatar"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    private var isChecked = false
    private var username: String? = null
    private var id = 0
    private var avatarUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME)
        val id = intent.getIntExtra(ID, 0)
        val avatarUrl = intent.getStringExtra(AVATAR)

        val bundle = Bundle()
        bundle.putString(USERNAME, username)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        viewModel.setDetail(username)
        viewModel.getDetail().observe(this) { detail ->
            if (detail != null) {
                binding.apply {
                    tvName.text = detail.name
                    tvUsername.text = detail.login
                    tvFollowers.text = resources.getString(R.string.followers, detail.followers)
                    tvFollowing.text = resources.getString(R.string.following, detail.following)
                    Glide.with(this@DetailActivity)
                        .load(detail.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfil)
                }
                showLoading(false)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null && count > 0) {
                    binding.favoriteButton.isChecked = true
                    isChecked = true
                } else {
                    binding.favoriteButton.isChecked = false
                }
            }
        }

        binding.favoriteButton.setOnClickListener {
            isChecked = !isChecked
            if (isChecked){
                viewModel.addFavorite(username!!, id, avatarUrl!!)
            }else{
                viewModel.uncheckUser(id)
            }
            binding.favoriteButton.isChecked = isChecked
        }

        showLoading(true)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
