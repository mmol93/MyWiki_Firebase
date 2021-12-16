package com.example.mywiki_interviewtest.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mywiki_interviewtest.Ext.setOnSingleClickListener
import com.example.mywiki_interviewtest.R
import com.example.mywiki_interviewtest.UI.adapter.WikiRecyclerAdapter
import com.example.mywiki_interviewtest.databinding.ActivityMainBinding
import com.example.mywiki_interviewtest.viewModel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var wikiAdapter : WikiRecyclerAdapter
    private val uploadFragment by lazy { UploadFragment() }
    private val wikiFragment by lazy { WikiFragment() }
    private lateinit var binding: ActivityMainBinding
    private var showCoverContainer = true
    private lateinit var viewModel : MyViewModel

    
    
    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding.lifecycleOwner = this

        
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        
        supportActionBar?.hide()

        
        binding.bottomNavigation.selectedItemId = R.id.item_wiki

        replaceFragment(wikiFragment, "wiki")

        binding.addButton.setOnSingleClickListener {
            if (showCoverContainer) {
                showCoverContainer = false
                binding.coverContainer.isGone = true
                binding.mainContainer.isGone = false
            }
        }

        initBottomNav()
    }
    override fun onBackPressed() {
        
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            finishAffinity()
            return
        } else {
            Toast.makeText(this, "Double tap to close app", Toast.LENGTH_SHORT).show()
        }
        mBackPressed = System.currentTimeMillis()
    }

    private fun initBottomNav() {
        binding.bottomNavigation.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_upload -> {
                        
                        if (showCoverContainer){
                            binding.coverContainer.isGone = false
                            binding.mainContainer.isGone = true
                        }

                        
                        if (supportFragmentManager.findFragmentByTag("home") != null){
                            showFragment(uploadFragment)
                        }else{
                            addFragment(uploadFragment, "home")
                        }
                        if (supportFragmentManager.findFragmentByTag("wiki") != null){
                            hideFragment(wikiFragment)
                        }

                    }
                    R.id.item_wiki -> {
                        
                        binding.coverContainer.isGone = true
                        binding.mainContainer.isGone = false

                        
                        if (supportFragmentManager.findFragmentByTag("home") != null){
                            hideFragment(uploadFragment)
                        }
                        if (supportFragmentManager.findFragmentByTag("wiki") != null){
                            showFragment(wikiFragment)
                        }else{
                            addFragment(wikiFragment, "wiki")
                        }

                    }
                }
                true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment, tag).commit()
    }
    private fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().show(fragment).commit()
    }
    private fun addFragment(fragment: Fragment, tag:String){
        supportFragmentManager.beginTransaction().add(R.id.mainContainer, fragment, tag).commit()
    }
    private fun hideFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().hide(fragment).commit()
    }
}