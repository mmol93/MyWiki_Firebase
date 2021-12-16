package com.example.mywiki_interviewtest.UI

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.mywiki_interviewtest.R
import com.example.mywiki_interviewtest.util.App
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

object FirebaseStorage {
    fun storageUpload(storagePath: String, imageData: Bitmap) {
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

    fun ImageView.storageDownload(storagePath: String) {
        val storage = Firebase.storage
        Log.d("Firebase", "post.title: ${storagePath}")
        val firebaseImagesRef =
            storage.getReferenceFromUrl("gs://mywiki-interviewtest.appspot.com/Wiki/$storagePath.jpg")

        if (firebaseImagesRef != null) {
            val ONE_MEGABYTE: Long = 1024 * 1024 * 20
            firebaseImagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                // byteArray를 bitmap으로 변환
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                this.findViewById<ImageView>(this.id).setImageBitmap(bitmap)
                Log.d("ProfileLayout", "picture load test")
            }.addOnFailureListener {
                // Handle any errors
                Toast.makeText(App.context, "profile download failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}