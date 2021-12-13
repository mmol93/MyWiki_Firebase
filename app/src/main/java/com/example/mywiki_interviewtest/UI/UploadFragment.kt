package com.example.mywiki_interviewtest.UI

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mywiki_interviewtest.Ext.setOnSingleClickListener
import com.example.mywiki_interviewtest.R
import com.example.mywiki_interviewtest.databinding.FragmentUploadBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UploadFragment : Fragment() {
    private lateinit var binding: FragmentUploadBinding
    private var profileBitmap: Bitmap? = null
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val imageUrl = it.data?.data
            if (it.resultCode == RESULT_OK && imageUrl != null){
                // Uri에 있는 image 데이터를 bitmap으로 변환하기
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    profileBitmap = ImageDecoder
                        .decodeBitmap(ImageDecoder.createSource(requireContext().getContentResolver(), imageUrl))
                }else{
                    profileBitmap = MediaStore
                        .Images.Media.getBitmap(requireContext().getContentResolver(), imageUrl)
                }
                Glide.with(requireContext()).load(imageUrl).into(binding.imageView)
                binding.imageView.isGone = false
                binding.addPictureButton.isGone = true
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentUploadBinding>(
            inflater,
            R.layout.fragment_upload,
            container,
            false
        )
        binding.clearButton.setOnSingleClickListener {
            // Make dialog for clear
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.apply {
                setTitle("Clear All?")
                setMessage("Do you really want to clear all?")
                setNeutralButton("No", null)
                setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                    binding.titleEditText.setText("")
                    binding.descriptionEditText.setText("")
                    binding.addPictureButton.isGone = false
                    binding.imageView.isGone = true
                }
                show()
            }
        }

        fun getPictureFromGallery(){
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY
            // startActivityForResult is deprecated
            startForResult.launch(intent)
        }

        binding.addPictureButton.setOnSingleClickListener {
            // 내부 파일을 가져오기 위해 필요한 권한
            val permission_list = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            var permissionChecker = 0

            // 권한 여부를 하나씩 확인 하기
            for (permission in permission_list) {
                // 권한 체크
                val check = checkCallingOrSelfPermission(requireContext(), permission)
                if (check == PackageManager.PERMISSION_GRANTED) {
                    permissionChecker = 1
                } else {
                    // 하나라도 허가 안된게 있을 때 -> 권한 요청
                    requestPermissions(permission_list, 0)
                    permissionChecker = 0
                }
            }
            // 모두 허가 되있을 때 -> 갤러리를 열어서 원하는 사진 선택
            if (permissionChecker == 1) {
                getPictureFromGallery()
            }
        }

        binding.imageView.setOnSingleClickListener {
            getPictureFromGallery()
        }

        binding.saveButton.setOnSingleClickListener {
            // put data in Firebase Cloud Store
            val db = Firebase.firestore

        }
        return binding.root
    }
}