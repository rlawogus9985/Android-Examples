package com.example.globalaos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.globalaos.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnScrollChangeListener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var tinyProfileScrollThreshold: Number
    private lateinit var indicatorAdapter: IndicatorAdapter
    private lateinit var indicatorRecyclerView: RecyclerView

    private val BTN_UP_SCROLL_THRESHOLD = 200
    private var isTinyProfileVisible = false


    private val imageUrlsList = arrayListOf(
        "https://cdn.newspenguin.com/news/photo/202009/3147_9206_5039.jpg",
        "https://t1.daumcdn.net/cfile/tistory/9952E24C5EA29AC52F",
        "https://t1.daumcdn.net/cfile/tistory/99F147485CE150491D",
        "https://img2.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202105/25/mbig/20210525090134676voqy.jpg",
        "https://jjal.today/data/file/gallery/1889155643_NZHvkRLz_e0292b65bb682075bfdb752a4d8f4062f0b7738a.png"
    )
    private val dots = ArrayList<TextView>(imageUrlsList.size)
    private val imageIds = listOf(
        R.drawable.img_painter_01,
        R.drawable.img_painter_02,
        R.drawable.img_painter_03,
        R.drawable.img_painter_04
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photoVideoImageView1.clipToOutline = true
        binding.photoVideoImageView2.clipToOutline = true
        binding.photoVideoImageView3.clipToOutline = true
        binding.photoVideoImageView4.clipToOutline = true
        binding.photoVideoImageView5.clipToOutline = true
        binding.tinyProfileImage.clipToOutline = true

        binding.scrollView.setOnScrollChangeListener(this)
        binding.scrollUpButton.setOnClickListener {
            binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
        }

        val viewPager = binding.viewPager2View
        viewPager.adapter =  ImageSliderAdapter(this, imageUrlsList)
        val adapter = viewPager.adapter
        viewPager.setCurrentItem(  Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % imageUrlsList.size , false)
        indicatorAdapter = IndicatorAdapter(imageUrlsList.size)
        indicatorRecyclerView.adapter = indicatorAdapter

        indicatorRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        //뷰페이저 페이지 변경 리스너 추가
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //인디케이터 어댑터의 현재 위치 업데이트
                val realPosition = position % imageUrlsList.size
                indicatorAdapter.setCurrentPosition(realPosition)
            }
        })
        //화면 위치 표시 생성

        //화면 변경 시 이벤트 설정
        binding.viewPager2View.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })


        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val params = binding.tinyProfileLayout.layoutParams
        params.height += getStatusBarHeight(this)
        binding.tinyProfileLayout.layoutParams = params

        val random = Random.Default
        val informationList = arrayListOf(
            Informations("First name","Mark serra", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Sexual Orientation","Female", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Ethnicity","Middle eastern", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Height","180cm", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Body type","Dignified", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Job","Model", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Personality","Easygoing", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Smoke","Trying to quit", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Drink","Only socially", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Religion","Christianity", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Education","High School Graduate", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Language","Japanese", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Child","None", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Parenting Plan","Discuss", random.nextInt(156),imageIds[random.nextInt(4)],null),
            Informations("Interests",this.getString(R.string.interests), random.nextInt(156),imageIds[random.nextInt(4)],imageIds[random.nextInt(4)]),
            Informations("Favorite Food",this.getString(R.string.favoriteFood), random.nextInt(156),imageIds[random.nextInt(4)],imageIds[random.nextInt(4)]),
            Informations("Exercise",this.getString(R.string.exercise), random.nextInt(156),imageIds[random.nextInt(4)],imageIds[random.nextInt(4)])
        )
        val rv_information = binding.recyclerViewInformation
        rv_information.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_information.setHasFixedSize(true)

        rv_information.adapter = InformationAdapter(informationList)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            val consLay = binding.secondInfoLayout
            tinyProfileScrollThreshold = consLay.top.toFloat()
            Log.d("abbb", "Top position of constraintLayout: $tinyProfileScrollThreshold")
        }
    }

    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }


    override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int
    ) {
        if (scrollY > BTN_UP_SCROLL_THRESHOLD) {
            binding.scrollUpButton.visibility = View.VISIBLE
        } else {
            binding.scrollUpButton.visibility = View.GONE
        }

        if (scrollY > tinyProfileScrollThreshold.toFloat()) {
            if (!isTinyProfileVisible) {
                binding.tinyProfileLayout.alpha = 0f
                binding.tinyProfileLayout.visibility = View.VISIBLE
                binding.tinyProfileLayout.translationY = -binding.tinyProfileLayout.height.toFloat()
                binding.tinyProfileLayout.animate().alpha(1f).translationY(0f).setDuration(500)
                    .withEndAction(null).start()
                isTinyProfileVisible = true
            }
        } else {
            if (isTinyProfileVisible) {
                binding.tinyProfileLayout.animate()
                    .alpha(0f)
                    .translationY(-binding.tinyProfileLayout.height.toFloat())
                    .setDuration(500)
                    .withEndAction {
                        binding.tinyProfileLayout.visibility = View.GONE
                    }
                    .start()
                isTinyProfileVisible = false
            }
        }
    }




}