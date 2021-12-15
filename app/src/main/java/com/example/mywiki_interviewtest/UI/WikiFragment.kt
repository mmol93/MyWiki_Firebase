package com.example.mywiki_interviewtest.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mywiki_interviewtest.R
import com.example.mywiki_interviewtest.databinding.FragmentWikiBinding
import com.example.mywiki_interviewtest.util.ApiResponse
import com.example.mywiki_interviewtest.viewModel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WikiFragment : Fragment() {
    private lateinit var binding: FragmentWikiBinding
    lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wiki, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            getPost()
        }
        return binding.root
    }

    suspend fun getPost(){
        viewModel.getPost().collect {
            when(it){
                is ApiResponse.Success -> {
                    coroutineScope {
                        launch(Dispatchers.Main) {
                            Log.d("WikiFragment", "getPost Data: ${it.data}")
                            binding.progressBar.isGone = true
                        }
                    }
                }
                is ApiResponse.Loading ->{
                    coroutineScope {
                        launch(Dispatchers.Main) {
                            binding.progressBar.isGone = false
                        }
                    }
                }
                is ApiResponse.Error -> {
                    Log.d("WikiFragment", "getPost Error: ${it.message}")
                }
            }
        }
    }
}