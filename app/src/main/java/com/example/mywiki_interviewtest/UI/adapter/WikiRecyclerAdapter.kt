package com.example.mywiki_interviewtest.UI.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mywiki_interviewtest.UI.FirebaseStorage.storageDownload
import com.example.mywiki_interviewtest.databinding.ItemWikiBinding
import com.example.mywiki_interviewtest.model.Post

class WikiRecyclerAdapter:RecyclerView.Adapter<WikiViewHolder>() {
    private val callBack = object : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    // diifer 인스턴스 생성
    val differ = AsyncListDiffer(this, callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WikiViewHolder {
        val binding = ItemWikiBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return WikiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WikiViewHolder, position: Int) {
        val post = differ.currentList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

class WikiViewHolder(val binding:ItemWikiBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post){
        binding.titleText.text = post.title
        binding.descriptionText.text = post.description

        // Firebase Storage에서 그림 가져와서 넣기
        Log.d("WikiAdapter", "post.title: ${post.title}")
        binding.mainImage.storageDownload(post.title)
    }
}