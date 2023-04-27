package com.example.dallamain

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dallamain.Adapter.FollowingAdapter
import com.example.dallamain.Adapter.TobBnrAdapter
import com.example.dallamain.Adapter.Top10Adapter
import com.example.dallamain.Data.FollowingData
import com.example.dallamain.Data.Top10Data
import com.example.dallamain.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
//        window.statusBarColor = Color.TRANSPARENT
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        // actionBarW 마진설정
        val params = binding.actionBarW.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(0, getStatusBarHeight(this,),0,0)
        binding.actionBarW.layoutParams = params

        binding.topbnrViewPager.apply{
            adapter = TobBnrAdapter(topBnrImages)
            setCurrentItem(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % topBnrImages.size)
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

    }

    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen","android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }

}