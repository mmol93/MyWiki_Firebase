package com.example.mywiki_interviewtest.viewModel

import androidx.lifecycle.ViewModel
import com.example.mywiki_interviewtest.model.Post
import com.example.mywiki_interviewtest.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(val repository:UploadRepository):ViewModel() {
    fun addPost(post: Post) = repository.addPost(post)
}
