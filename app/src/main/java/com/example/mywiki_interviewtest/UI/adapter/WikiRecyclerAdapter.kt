package com.example.mywiki_interviewtest.UI.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mywiki_interviewtest.Ext.setOnSingleClickListener
import com.example.mywiki_interviewtest.util.FirebaseStorage.storageDownload
import com.example.mywiki_interviewtest.databinding.ItemWikiBinding
import com.example.mywiki_interviewtest.model.Post

class WikiRecyclerAdapter() : RecyclerView.Adapter<WikiRecyclerAdapter.WikiViewHolder>() {
    // listener: (Post) -> Unit 의미: Post를 매개변수로 받고 아무것도 반환하지 않는 함수를 의미
    // 즉, 보기에는 변수처럼 보여도 실제로는 Post를 매개변수로 받는 함수다
    private var onItemClickListener: ((Post, view: View) -> Unit)? = null

    private val callBack = object : DiffUtil.ItemCallback<Post>() {
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
        holder.bind(post, holder.itemView)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Post, view:View) -> Unit) {
        onItemClickListener = listener
    }


    inner class WikiViewHolder(val binding: ItemWikiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post, view:View) {
            binding.titleText.text = post.title
            binding.descriptionText.text = post.description

            // Firebase Storage에서 그림 가져와서 넣기
            Log.d("WikiAdapter", "post.title: ${post.title}")
            binding.mainImage.storageDownload(post.title)

            // 클릭 시 해당 Post 데이터 넘겨줌
            binding.root.setOnSingleClickListener {

                // 즉, onItemClickListener 익명함수를 실행해라
                // onItemClickListener.involke(post) 동일
                onItemClickListener?.let {
                    it(post, view)
                }
            }
        }
    }
}