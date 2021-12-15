package com.example.mywiki_interviewtest.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

    // 뒤로가기 두번 연속 클릭으로 종료 변수 설정
    // 2 초내에 더블 클릭시...
    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding.lifecycleOwner = this

        // initial fragment is wikiFragment
        binding.bottomNavigation.selectedItemId = R.id.item_wiki
//        replaceFragment(wikiFragment)
        addFragment(wikiFragment, "wiki")

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
        // 연속 두 번 클릭하여 종료하기
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
                        // add 버튼을 한 번도 클릭 안했을 때는 uploadFragment 올 때 마다 coverContainer 보여주기
                        if (showCoverContainer){
                            binding.coverContainer.isGone = false
                            binding.mainContainer.isGone = true
                        }

                        // fragment를 보여주거나 숨기거나
                        if (supportFragmentManager.findFragmentByTag("home") != null){
                            showFragment(uploadFragment)
                        }else{
                            addFragment(uploadFragment, "home")
                        }
                        if (supportFragmentManager.findFragmentByTag("wiki") != null){
                            hideFragment(wikiFragment)
                        }
//                        replaceFragment(uploadFragment)
                    }
                    R.id.item_wiki -> {
                        // wikiFragment에서는 항상 coverFragment 가리기
                        binding.coverContainer.isGone = true
                        binding.mainContainer.isGone = false

                        // fragment를 보여주거나 숨기거나
                        if (supportFragmentManager.findFragmentByTag("home") != null){
                            hideFragment(uploadFragment)
                        }
                        if (supportFragmentManager.findFragmentByTag("wiki") != null){
                            showFragment(wikiFragment)
                        }else{
                            addFragment(wikiFragment, "wiki")
                        }
//                        replaceFragment(wikiFragment)
                    }
                }
                true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit()
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