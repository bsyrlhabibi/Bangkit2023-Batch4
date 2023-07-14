package com.bibi.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bibi.storyapp.data.model.Story
import com.bibi.storyapp.databinding.ActivityDetailBinding
import com.bibi.storyapp.withDateFormat
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = intent.getParcelableExtra<Story>(EXTRA_STORY)

        binding.apply {
            tvDetailName.text = story?.name
            tvDetailDate.text = story?.createdAt?.withDateFormat()
            tvDetailDescription.text = story?.description
            Glide.with(binding.root)
                .load(story?.photoUrl)
                .into(ivDetailPhoto)
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}