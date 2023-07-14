package com.bibi.storyapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bibi.storyapp.data.remote.response.ListStory
import com.bibi.storyapp.databinding.ActivityDetailBinding
import com.bibi.storyapp.utils.withDateFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_STORY = "extra_story"
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val story = intent.getParcelableExtra("extra_story") as ListStory?

        binding.tvDetailName.text =story?.name
        binding.tvDetailDate.text = story?.createdAt?.withDateFormat()
        binding.tvDetailDescription.text = story?.description.toString()
        Glide.with(this@DetailActivity).load(story?.photoUrl).into(binding.ivDetailPhoto)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}