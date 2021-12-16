package com.example.mywiki_interviewtest.UI

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.Pair
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywiki_interviewtest.R
import com.example.mywiki_interviewtest.UI.adapter.WikiRecyclerAdapter
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
    private lateinit var viewModel: MyViewModel
    lateinit var wikiAdapter : WikiRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wiki, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        wikiAdapter = (activity as MainActivity).wikiAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            getPost()
        }
        initRecyclerView()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            CoroutineScope(Dispatchers.IO).launch {
                getPost()
            }
        }
        wikiAdapter.setOnItemClickListener { post, view ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("post", post)

            val intentOption = ActivityOptions.makeSceneTransitionAnimation(
                requireActivity(),
                Pair.create(view.findViewById(R.id.descriptionText), "detailTransit")
            )
            startActivity(intent, intentOption.toBundle())
        }
    }


    fun initRecyclerView(){
        binding.wikiRecycler.apply {
            adapter = wikiAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    suspend fun getPost(){
        viewModel.getPost().collect {
            when(it){
                is ApiResponse.Success -> {
                    coroutineScope {
                        launch(Dispatchers.Main) {
                            Log.d("WikiFragment", "getPost Data: ${it.data}")
                            binding.progressBar.isGone = true
                            wikiAdapter.differ.submitList(it.data)
                            binding.swipeRefresh.isRefreshing = false
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