package com.dev.storyapp.view.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dev.storyapp.R
import com.dev.storyapp.data.model.ListStoryItem
import com.dev.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Dicoding Story"

        val data = intent.getParcelableExtra<ListStoryItem>("DATA")
        val bannerPhoto: ImageView = binding.banner
        val tvJudul: TextView = binding.judul
        val tvDeskripsi: TextView = binding.deskripsi
        if (data != null) {
            tvJudul.text = data.name
            tvDeskripsi.text = data.description
            Glide.with(this)
                .load(data.photoUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
                )
                .into(bannerPhoto)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}