package com.example.globalaos

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.globalaos.Adapter.IndicatorAdapter
import com.example.globalaos.Adapter.InformationAdapter
import com.example.globalaos.Adapter.PhotoAdapter
import com.example.globalaos.Data.Informations
import com.example.globalaos.databinding.ActivityMainBinding
import com.example.globalaos.databinding.PopOverBinding
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnScrollChangeListener {

    // 뷰바인딩
    private lateinit var binding: ActivityMainBinding
    // 미니프로필 임계점 높이
    private lateinit var tinyProfileScrollThreshold: Number
    // photo 레이아웃 가로길이
    private var photoLayoutWidth: Int = 0
    // 업스크롤버튼 임계점
    private val BTN_UP_SCROLL_THRESHOLD = 200
    // 미니프로필 Boolean
    private var isTinyProfileVisible = false

    // 뷰페이저2에 들어갈 이미지 주소
    private val imageUrlsList = arrayListOf(
        "https://cdn.newspenguin.com/news/photo/202009/3147_9206_5039.jpg",
        "https://t1.daumcdn.net/cfile/tistory/9952E24C5EA29AC52F",
        "https://t1.daumcdn.net/cfile/tistory/99F147485CE150491D",
        "https://img2.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202105/25/mbig/20210525090134676voqy.jpg",
        "https://jjal.today/data/file/gallery/1889155643_NZHvkRLz_e0292b65bb682075bfdb752a4d8f4062f0b7738a.png"
    )

    // painter 이미지 리스트
    private val imageIds = listOf(
        R.drawable.img_painter_01,
        R.drawable.img_painter_02,
        R.drawable.img_painter_03,
        R.drawable.img_painter_04
    )
    // 다운받은 사진들 리스트
    private val imageIds2 = arrayOf(
        R.drawable.robert1,
        R.drawable.robert2,
        R.drawable.robert3,
        R.drawable.robert4,
        R.drawable.robert5,
        R.drawable.penguin1,
        R.drawable.penguin1
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 만약 photo/video에서 recylcerview + gridLayoutManager를 안쓴다면
//        val imageViews = arrayOf(
////            binding.photoVideoImageView1,
////            binding.photoVideoImageView2,
////            binding.photoVideoImageView3,
////            binding.photoVideoImageView4,
////            binding.photoVideoImageView5,
//            binding.tinyProfileImage
//        )
//
//        for (i in imageViews.indices){
//            Glide.with(this).load(imageIds2[i]).into(imageViews[i])
//            imageViews[i].clipToOutline = true
//        }

        // 미니프로필 사진 추가+가공
        Glide.with(this).load(imageIds2[5]).into(binding.tinyProfileImage)
        binding.tinyProfileImage.clipToOutline = true

        binding.scrollView.setOnScrollChangeListener(this)
        binding.scrollUpButton.setOnClickListener {
            binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
        }

        // 뷰페이저2 어뎁터 연결
        val viewPager = binding.viewPager2View
        viewPager.adapter =  ImageSliderAdapter(imageUrlsList)
//        val adapter = viewPager.adapter
        // 뷰페이저2 시작 위치 조절
        val startPosition = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % imageUrlsList.size
        viewPager.setCurrentItem(startPosition, false)

        // RecyclerView를 활용한 indicator연결
        val indicatorRecyclerView = binding.indicatorRecycleView
        val indicatorAdapter = IndicatorAdapter(imageUrlsList.size)
        indicatorRecyclerView.adapter = indicatorAdapter
        indicatorRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // 오픈소스를 활용한 circleIndicator3
        val indicator = binding.ciIndicator
//        indicator.setViewPager(viewPager)
        indicator.createIndicators(imageUrlsList.size, 0)

        // 오픈소스를 활용한 zhpanIndicator
        val zhpanIndicator = binding.zhpanIndicator
        val checkedColor = ContextCompat.getColor(this, R.color.primary_500)
        val normalColor = ContextCompat.getColor(this, R.color.gray_f_1)
        zhpanIndicator?.apply{
            setSliderColor(normalColor,checkedColor)
            setSliderWidth(resources.getDimension(R.dimen.dp_8))
            setSlideMode(IndicatorSlideMode.NORMAL)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setPageSize(imageUrlsList.size)
            notifyDataChanged()
        }

        //뷰페이저 페이지 변경 리스너 추가
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //인디케이터 어댑터의 현재 위치 업데이트
                val realPosition = position % imageUrlsList.size
                indicatorAdapter.setCurrentPosition(realPosition)
                indicator.animatePageSelected(realPosition)
                if (zhpanIndicator != null) {
                    zhpanIndicator.onPageSelected(realPosition)
                }
            }
        })

        // 전체화면, 상태바 없애기
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // statusbar 고려 tinyProfileLayout 설정
        val params = binding.tinyProfileLayout.layoutParams as ViewGroup.MarginLayoutParams
        params.height += getStatusBarHeight(this)
        params.setMargins(0, -params.height, 0, 0)
        binding.tinyProfileLayout.layoutParams = params
        // 레이아웃 밑에 깔린 터치 막기
        binding.tinyProfileLayout.setOnTouchListener { _, _ -> true }

        // statusbar 고려 backDetailLayout 마진 설정
        val params2 = binding.backDetailLayout.layoutParams as ViewGroup.MarginLayoutParams
        params2.setMargins(0,getStatusBarHeight(this),0,0)
        binding.backDetailLayout.layoutParams = params2

        // information RecylceView를 위한 데이터 추가
        val random = Random.Default
        val informationList = arrayListOf(
            Informations("First name","Mark serra", random.nextInt(156),imageIds.random(),null),
            Informations("Sexual Orientation","Female", random.nextInt(156),imageIds.random(),null),
            Informations("Ethnicity","Middle eastern", random.nextInt(156),imageIds.random(),null),
            Informations("Height","180cm", random.nextInt(156),imageIds.random(),null),
            Informations("Body type","Dignified", random.nextInt(156),imageIds.random(),null),
            Informations("Job","Model", random.nextInt(156),imageIds.random(),null),
            Informations("Personality","Easygoing", random.nextInt(156),imageIds.random(),null),
            Informations("Smoke","Trying to quit", random.nextInt(156),imageIds.random(),null),
            Informations("Drink","Only socially", random.nextInt(156),imageIds.random(),null),
            Informations("Religion","Christianity", random.nextInt(156),imageIds.random(),null),
            Informations("Education","High School Graduate", random.nextInt(156),imageIds.random(),null),
            Informations("Language","Japanese", random.nextInt(156),imageIds.random(),null),
            Informations("Child","None", random.nextInt(156),imageIds.random(),null),
            Informations("Parenting Plan","Discuss", random.nextInt(156),imageIds.random(),null),
            Informations("Interests",this.getString(R.string.interests), random.nextInt(156),imageIds.random(),imageIds.random()),
            Informations("Favorite Food",this.getString(R.string.favoriteFood), random.nextInt(156),imageIds.random(),imageIds.random()),
            Informations("Exercise",this.getString(R.string.exercise), random.nextInt(156),imageIds.random(),imageIds.random())
        )
        // information RecylcerView 어뎁터 추가
        val rv_information = binding.recyclerViewInformation
        rv_information.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_information.setHasFixedSize(true)
        rv_information.adapter = InformationAdapter(informationList)

        // report/block 버튼을 위한 팝업윈도우 생성
        val popupBinding = PopOverBinding.inflate(layoutInflater)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupBinding.root, width, height, focusable)

        fun showPopupWindow() {
            popupWindow.showAtLocation(binding.btnDetailMore, Gravity.NO_GRAVITY,
                binding.popUpLayout.x.toInt() ,
                binding.popUpLayout.y.toInt())
            popupWindow.setOnDismissListener {  }
        }

        binding.btnDetailMore.setOnClickListener{
            showPopupWindow()
        }

        binding.btnDetailMoreW?.setOnClickListener {
            showPopupWindow()
        }

        popupBinding.blockButton.setOnClickListener{
            Toast.makeText(this, "block",Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
        }
        popupBinding.reportButton.setOnClickListener{
            Toast.makeText(this, "report",Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
        }


        // photo/video를 위한 RecyclerView연결
        val photoRV = binding.photoRecylcerView
        // 화면이 다 그려지면 나오는 콜백
        photoRV.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                photoLayoutWidth = photoRV.width
                val spanCount = photoLayoutWidth.div(98.dpToPx())
                Log.d("sdfs","${spanCount}")
                val photoLM = GridLayoutManager(this@MainActivity, spanCount)
                photoRV.adapter = PhotoAdapter(imageIds2)
                photoRV.layoutManager = photoLM
                photoRV.addItemDecoration(ItemPhotoDecoration(photoLayoutWidth,spanCount,98.dpToPx()))

                // 리스너 삭제
                photoRV.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            val consLay = binding.secondInfoLayout
            tinyProfileScrollThreshold = consLay.top.toFloat()
            Log.d("abbb", "Top position of constraintLayout: $tinyProfileScrollThreshold")
            Log.d("abbb", "photolayoutwidth: $photoLayoutWidth")

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
                val slideDown = ObjectAnimator.ofFloat(binding.tinyProfileLayout,
                    "translationY",
                    binding.tinyProfileLayout.height.toFloat())
                slideDown.duration = 500
                slideDown.start()
                isTinyProfileVisible = true
            }
        } else {
            if (isTinyProfileVisible) {
                val slideDown = ObjectAnimator.ofFloat(binding.tinyProfileLayout,
                    "translationY",
                    -binding.tinyProfileLayout.height.toFloat())
                slideDown.duration = 500
                slideDown.start()
                isTinyProfileVisible = false
            }
        }
    }

    fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}