package com.example.mywiki_interviewtest.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mywiki_interviewtest.model.Post
import com.example.mywiki_interviewtest.repository.UploadRepository
import com.example.mywiki_interviewtest.repository.WikiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
        val uploadRepository:UploadRepository
        , val wikiRepository: WikiRepository):ViewModel() {
    var savePermission = MutableLiveData<Boolean>()
    init {
        savePermission.value = false
    }
    fun addPost(post: Post) = uploadRepository.addPost(post)
    fun getPost() = wikiRepository.getPost()
}
