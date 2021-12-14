package com.example.mywiki_interviewtest.UI

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

object FirebaseStorage {
    fun storageUpload(storagePath:String, imageData:Bitmap){
        val storage = Firebase.storage
        val firebaseStorageRef = storage.reference
        val imageRef = firebaseStorageRef.child(storagePath)
        val baos = ByteArrayOutputStream()
        imageData.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Log.d("Firebase", "imageUpload Failed")
        }.addOnSuccessListener { taskSnapshot ->
            Log.d("Firebase", "imageUpload Success")
        }
    }
}