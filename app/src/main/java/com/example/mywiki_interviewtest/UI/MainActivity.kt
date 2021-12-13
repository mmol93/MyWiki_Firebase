package com.example.mywiki_interviewtest.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mywiki_interviewtest.Ext.setOnSingleClickListener
import com.example.mywiki_interviewtest.R
import com.example.mywiki_interviewtest.databinding.ActivityMainBinding
import com.example.mywiki_interviewtest.viewModel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val uploadFragment by lazy { UploadFragment() }
    private val wikiFragment by lazy { WikiFragment() }
    private lateinit var binding: ActivityMainBinding
    private var showCoverContainer = true
    private lateinit var viewModel : MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        // initial fragment is wikiFragment
        binding.bottomNavigation.selectedItemId = R.id.item_wiki
        replaceFragment(wikiFragment)

        binding.addButton.setOnSingleClickListener {
            if (showCoverContainer) {
                showCoverContainer = false
                binding.coverContainer.isGone = true
                binding.mainContainer.isGone = false
            }
        }

        initBottomNav()
    }

    private fun initBottomNav() {
        binding.bottomNavigation.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_upload -> {
                        // add 버튼을 한 번도 클릭 안했을 때는 uploadFragment 올 때 마다 coverContainer 보여주기
                        if (showCoverContainer){
                            binding.coverContainer.isGone = false
                            binding.mainContainer.isGone = true
                        }
                        replaceFragment(uploadFragment)
                    }
                    R.id.item_wiki -> {
                        // wikiFragment에서는 항상 coverFragment 가리기
                        binding.coverContainer.isGone = true
                        binding.mainContainer.isGone = false
                        replaceFragment(wikiFragment)
                    }
                }
                true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit()
    }
}