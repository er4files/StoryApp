package com.dev.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.storyapp.R
import com.dev.storyapp.data.model.ListStoryItem
import com.dev.storyapp.view.adapter.StoryAdapter
import com.dev.storyapp.databinding.ActivityMainBinding
import com.dev.storyapp.view.ViewModelFactory
import com.dev.storyapp.view.detail.DetailActivity
import com.dev.storyapp.view.upload.UploadActivity
import com.dev.storyapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val recyclerView = binding.rcliststory
        storyAdapter = StoryAdapter()
        recyclerView.adapter = storyAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                viewModel.getStories(user.token)

                viewModel.storyResponse.observe(this) { response ->
                    if (response != null && response.error == false) {
                        binding.progressBar.visibility = View.INVISIBLE
                        storyAdapter.submitList(response.listStory)
                        storyAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ListStoryItem) {
                                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                                intentToDetail.putExtra("DATA", data)
                                startActivity(intentToDetail)
                            }
                        })
                    } else {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this@MainActivity, "Gagal mendapatkan data dari API", Toast.LENGTH_SHORT).show()
                    }
                }

                binding.topAppBar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu1 -> {
                            val intent = Intent(this@MainActivity, UploadActivity::class.java)
                            startActivity(intent)
                            true
                        }
                        R.id.action_logout -> {
                            viewModel.logout()
                            true
                        }
                        else -> false
                    }
                }

            }
        }
    }

}