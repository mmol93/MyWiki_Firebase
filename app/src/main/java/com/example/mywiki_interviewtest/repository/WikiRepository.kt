package com.example.mywiki_interviewtest.repository

import android.util.Log
import com.example.mywiki_interviewtest.model.Post
import com.example.mywiki_interviewtest.util.ApiResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.protobuf.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class WikiRepository {
    val storageCollection = Firebase.firestore.collection("wiki")

    fun getPost() = flow<ApiResponse<List<Post>>> {
        val posts = ArrayList<Post>()

        emit(ApiResponse.Loading())

        // 모든 문서 가져오기
        val documents = storageCollection.get().await()
        for (document in documents){
            val postData = document.data
            posts.add(Post(title = postData.keys.toString(), description = postData.values.toString()))
        }
//        Log.d("Firebase", "posts: $posts")
        emit(ApiResponse.Success(posts))

    }.catch {
        emit(ApiResponse.Error("Error: ${it.message}"))
    }.flowOn(Dispatchers.IO)
}