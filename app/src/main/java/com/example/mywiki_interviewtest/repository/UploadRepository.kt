package com.example.mywiki_interviewtest.repository

import android.util.Log
import com.example.mywiki_interviewtest.model.Post
import com.example.mywiki_interviewtest.util.ApiResponse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UploadRepository {
    val db = Firebase.firestore.collection("wiki")

    fun addPost(post: Post) = flow<ApiResponse<Post>> {
        emit(ApiResponse.Loading())
        val postData = hashMapOf(
            post.title to post.description
        )
        Log.d("Firebase", "addPost")
        db.add(postData)
        emit(ApiResponse.Success(post))
    }.catch {
        emit(ApiResponse.Error("Error: ${it.message}"))
    }.flowOn(Dispatchers.IO)
}