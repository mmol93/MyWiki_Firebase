package com.example.mywiki_interviewtest.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mywiki_interviewtest.R
import com.example.mywiki_interviewtest.databinding.ActivityDetailBinding
import android.content.Intent
import com.example.mywiki_interviewtest.Ext.setOnSingleClickListener
import com.example.mywiki_interviewtest.UI.FirebaseStorage.storageDownload
import com.example.mywiki_interviewtest.model.Post


class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val intent = intent
        val post = intent.getSerializableExtra("post") as Post

        binding.detailText.text = post.description
        binding.detailImage.storageDownload(post.title)

        supportActionBar?.title = post.title

        binding.detailImage.setOnSingleClickListener {

        }
    }
}