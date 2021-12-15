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
            // 항상 0번 인덱스에 원하는 값이 있기 때문에 0번 인덱스의 값을 가져오게 한다
            posts.add(Post(title = postData.keys.elementAt(0), description = postData.values.elementAt(0).toString()))
        }
        Log.d("Firebase", "posts: $posts")
        emit(ApiResponse.Success(posts))

    }.catch {
        emit(ApiResponse.Error("Error: ${it.message}"))
    }.flowOn(Dispatchers.IO)
}