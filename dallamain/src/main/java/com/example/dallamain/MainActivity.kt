package com.example.dallamain

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.dallamain.Adapter.*
import com.example.dallamain.Data.FollowingData
import com.example.dallamain.Data.LiveSectionData
import com.example.dallamain.Data.Top10Data
import com.example.dallamain.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var job: Job
    private lateinit var job2: Job

    private val topBnrImages = arrayListOf(
        "https://image.dongascience.com/Photo/2021/08/8acffba1c970ee9d64c8ae98036b3028.jpg",
        "https://cdn.huffingtonpost.kr/news/photo/201804/68154_130067.jpeg",
        "https://post-phinf.pstatic.net/MjAxNzA5MDFfMTUy/MDAxNTA0MjQ4ODQ2MDYy.rFYrZCWFw_MmimDnG2jsnm5_sd-vxwIBK6d11Iq22mog.fPrPc-0xtvsv4GJWvOgTx1zeScl64oniSZg9hWPG4z4g.JPEG/GettyImages-523882338.jpg?type=w1200"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 상태표시줄 없애기
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            val controller = window.decorView.windowInsetsController
//            controller?.hide(WindowInsets.Type.statusBars())
//            controller?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        } else {
//            @Suppress("DEPRECATION")
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//        }
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
//        window.statusBarColor = Color.TRANSPARENT
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS , WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding.footerLayout.itemIconTintList = null
        val navigationBarHeight = getNavigationBarHeight(this)
        Log.d("jaehyeon","${navigationBarHeight}")
        val params2 = binding.footerLayout.layoutParams as ViewGroup.MarginLayoutParams
        params2.setMargins(0,0,0,navigationBarHeight)
        binding.footerLayout.layoutParams = params2

            // actionBarW 마진설정
            val params = binding.actionBarW.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, getStatusBarHeight(this,),0,0)
            binding.actionBarW.layoutParams = params

            binding.topbnrViewPager.apply{
                adapter = TobBnrAdapter(topBnrImages)
                setCurrentItem(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % topBnrImages.size)
                registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                        when (state) {
                            ViewPager2.SCROLL_STATE_IDLE -> {
                                if (!job.isActive) topBnrAutoScroll()
                            }
                            ViewPager2.SCROLL_STATE_DRAGGING -> job.cancel()
                        }
                    }
                })
            }


            val followingList = arrayListOf(
                FollowingData("https://og-data.s3.amazonaws.com/media/artworks/half/A0880/A0880-0016.jpg","가나다"),
                FollowingData("https://img.lovepik.com/photo/40173/9974.jpg_wh860.jpg","라마바사"),
                FollowingData("https://img.freepik.com/free-photo/beautiful-landscape_1417-1582.jpg","아자차"),
                FollowingData("https://t1.daumcdn.net/cfile/tistory/99B192495BA481842D","카타파하"),
                FollowingData("https://newsimg-hams.hankookilbo.com/2022/04/08/fdc1edb7-1352-48b2-8b5d-1b8906c3edfa.jpg","ABCDE")
            )

            binding.followingRecylcerView.apply{
                adapter = FollowingAdapter(followingList)
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            val top10List = arrayListOf(
                Top10Data("https://og-data.s3.amazonaws.com/media/artworks/half/A0880/A0880-0016.jpg","가나다", R.drawable.number_w_1),
                Top10Data("https://img.lovepik.com/photo/40173/9974.jpg_wh860.jpg","라마바사",R.drawable.number_w_2),
                Top10Data("https://img.freepik.com/free-photo/beautiful-landscape_1417-1582.jpg","아자차",R.drawable.number_w_3),
                Top10Data("https://t1.daumcdn.net/cfile/tistory/99B192495BA481842D","카타파하",R.drawable.number_w_4),
                Top10Data("https://newsimg-hams.hankookilbo.com/2022/04/08/fdc1edb7-1352-48b2-8b5d-1b8906c3edfa.jpg","ABCDE",R.drawable.number_w_5),
                Top10Data("https://og-data.s3.amazonaws.com/media/artworks/half/A0880/A0880-0016.jpg","가나다",R.drawable.number_w_6),
                Top10Data("https://img.lovepik.com/photo/40173/9974.jpg_wh860.jpg","라마바사",R.drawable.number_w_7),
                Top10Data("https://img.freepik.com/free-photo/beautiful-landscape_1417-1582.jpg","아자차",R.drawable.number_w_8),
                Top10Data("https://t1.daumcdn.net/cfile/tistory/99B192495BA481842D","카타파하",R.drawable.number_w_9),
                Top10Data("https://newsimg-hams.hankookilbo.com/2022/04/08/fdc1edb7-1352-48b2-8b5d-1b8906c3edfa.jpg","ABCDE",R.drawable.number_w_10)
            )

            binding.top10Recylcerview.apply{
                adapter = Top10Adapter(top10List)
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            val bannerList = arrayListOf(
                R.drawable.ad_02,
                R.drawable.ad_02,
                R.drawable.ad_02,
                R.drawable.ad_02,
                R.drawable.ad_02
            )

            binding.bannerViewPager.apply{
                adapter = BannerAdapter(bannerList)
                setCurrentItem(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % bannerList.size)
                binding.bannerText.text = getString(R.string.banner_count,1, bannerList.size)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        val realPosition = position % bannerList.size
                        binding.bannerText.text = getString(R.string.banner_count,realPosition + 1, bannerList.size)
                    }
                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                        when (state) {
                            ViewPager2.SCROLL_STATE_IDLE -> {
                                if (!job2.isActive) adBnrAutoScroll()
                            }
                            ViewPager2.SCROLL_STATE_DRAGGING -> job2.cancel()
                        }
                    }
                })
            }

            val tabLayout = binding.tabLayoutLiveSection
            val tab1: TabLayout.Tab = tabLayout.newTab()
            tab1.text = "전체"
            tabLayout.addTab(tab1)
            val tab2: TabLayout.Tab = tabLayout.newTab()
            tab2.text = "VIDEO"
            tabLayout.addTab(tab2)
            val tab3: TabLayout.Tab = tabLayout.newTab()
            tab3.text = "RADIO"
            tabLayout.addTab(tab3)
            val tab4: TabLayout.Tab = tabLayout.newTab()
            tab4.text = "신입DJ"
            tabLayout.addTab(tab4)


            val liveSectionList = arrayListOf(
                LiveSectionData(R.drawable.dog1,R.drawable.bdg_contents,null,"오늘도 화이팅하세요~!!!",R.drawable.ico_booster,
                    R.drawable.ico_female,"쏭디제이",R.drawable.people_g_s,"22",R.drawable.ico_booster_2,"234"),
                LiveSectionData(R.drawable.dog1,null,null,"달빛리뉴얼 예쁘게 잘나왔으면..",null,
                    R.drawable.ico_female,"쏭디제이",R.drawable.people_g_s,"22",R.drawable.ico_booster_2,"234"),
                LiveSectionData(R.drawable.dog1,R.drawable.ico_cupid_02,R.drawable.ico_cupid_03,"달빛 가족 화이팅 하시구요!!",R.drawable.ico_booster,
                    R.drawable.ico_male,"쏭디제이",R.drawable.people_g_s,"22",R.drawable.heart,"234"),
                LiveSectionData(R.drawable.dog1,R.drawable.bdg_star,null,"달빛 가족 화이팅 하시구요!!",R.drawable.ico_booster,
                    R.drawable.ico_female,"쏭디제이",R.drawable.people_g_s,"22",R.drawable.heart,"234"),
                LiveSectionData(R.drawable.dog1,R.drawable.bdg_newdj,R.drawable.ico_cupid_01,"달빛 가족 화이팅 하시구요!!",R.drawable.ico_booster,
                    R.drawable.ico_female,"쏭디제이",R.drawable.people_g_s,"22",R.drawable.heart,"234"),
                LiveSectionData(R.drawable.dog1,null,null,"달빛 가족 화이팅 하시구요!!",null,
                    R.drawable.ico_male,"쏭디제이",R.drawable.people_g_s,"22",R.drawable.heart,"234"),
            )

            binding.liveSectionRecylcerView.apply{
                adapter = LiveSectionAdapter(liveSectionList)
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            }

//        val typeface = ResourcesCompat.getFont(this, R.font.suit_bold)
            val spannable = SpannableString(binding.announceText.text)
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                5,9,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                12,20,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.announceText.text = spannable
        }

    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen","android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }
    fun getNavigationBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen","android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }

    fun startAutoScroll(interval: Long, viewPager: ViewPager2){
        GlobalScope.launch(Dispatchers.Main){
            while (true) {
                delay(interval)
                val current = viewPager.currentItem
                val next = (current + 1) % viewPager.adapter?.itemCount!!
                viewPager.setCurrentItem(next, true)
            }
        }
    }
    fun topBnrAutoScroll(){
        job = lifecycleScope.launch{
            delay(3500)
            binding.topbnrViewPager.setCurrentItem(binding.topbnrViewPager.currentItem + 1, true)
        }
    }
    fun adBnrAutoScroll(){
        job2 = lifecycleScope.launch{
            delay(3500)
            binding.bannerViewPager.setCurrentItem(binding.bannerViewPager.currentItem + 1, true)
        }
    }

    override fun onResume() {
        super.onResume()
        topBnrAutoScroll()
        adBnrAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
        job2.cancel()
    }

    }

