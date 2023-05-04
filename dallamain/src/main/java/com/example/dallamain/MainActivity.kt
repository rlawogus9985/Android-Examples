package com.example.dallamain

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.dallamain.Adapter.*
import com.example.dallamain.ApiService.RetrofitService
import com.example.dallamain.Data.*
import com.example.dallamain.Manager.RetrofitManager
import com.example.dallamain.databinding.ActivityMainBinding
import com.example.dallamain.databinding.ItemTopbnrBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnScrollChangeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var job: Job
    private lateinit var job2: Job
    private lateinit var service: RetrofitService

    private var topBnrLists : ArrayList<TopBnrData> = arrayListOf()
    private var followingList : ArrayList<FollowingData> = arrayListOf()
    private var eventLists: ArrayList<EventBannerData> = arrayListOf()
    private var liveSectionLists: ArrayList<LiveSectionData> = arrayListOf()
    private var CHANGE_BOUNDARY: Int = 0
    private var actionBarWHeight: Int = 0
    private var isTabLayoutSticked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.scrollView.setOnScrollChangeListener(this)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS , WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        binding.footerLayout.itemIconTintList = null
        val navigationBarHeight = getNavigationBarHeight(this)
        Log.d("jaehyeon","${navigationBarHeight}")
        val params2 = binding.footerLayout.layoutParams as ViewGroup.MarginLayoutParams
        params2.setMargins(0,0,0,navigationBarHeight)
        binding.footerLayout.layoutParams = params2

        // actionBarW 높이설정
        val params = binding.actionBarW.layoutParams as ViewGroup.MarginLayoutParams
        params.height += getStatusBarHeight(this)
        binding.actionBarW.layoutParams = params

        binding.actionBarW.setOnTouchListener { _, _ -> true }

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        // topBnr Api연결 및 Adapter연결
        getTopBnrList()

        // 팔로우 Api연결 및 Adapter연결
        getFollowingList()

        // event banner api연결 및 Adapter연결
        getEvenetList()

        val top10List = arrayListOf(
            Top10Data("https://og-data.s3.amazonaws.com/media/artworks/half/A0880/A0880-0016.jpg", "가나다", R.drawable.number_w_1),
            Top10Data("https://img.lovepik.com/photo/40173/9974.jpg_wh860.jpg", "라마바사", R.drawable.number_w_2),
            Top10Data("https://img.freepik.com/free-photo/beautiful-landscape_1417-1582.jpg", "아자차", R.drawable.number_w_3),
            Top10Data("https://t1.daumcdn.net/cfile/tistory/99B192495BA481842D", "카타파하", R.drawable.number_w_4),
            Top10Data("https://newsimg-hams.hankookilbo.com/2022/04/08/fdc1edb7-1352-48b2-8b5d-1b8906c3edfa.jpg", "ABCDE", R.drawable.number_w_5),
            Top10Data("https://og-data.s3.amazonaws.com/media/artworks/half/A0880/A0880-0016.jpg", "가나다", R.drawable.number_w_6),
            Top10Data("https://img.lovepik.com/photo/40173/9974.jpg_wh860.jpg", "라마바사", R.drawable.number_w_7),
            Top10Data("https://img.freepik.com/free-photo/beautiful-landscape_1417-1582.jpg", "아자차", R.drawable.number_w_8),
            Top10Data("https://t1.daumcdn.net/cfile/tistory/99B192495BA481842D", "카타파하", R.drawable.number_w_9),
            Top10Data("https://newsimg-hams.hankookilbo.com/2022/04/08/fdc1edb7-1352-48b2-8b5d-1b8906c3edfa.jpg", "ABCDE", R.drawable.number_w_10)
        )

        binding.top10Recylcerview.apply {
            adapter = Top10Adapter(top10List)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }


        val tabLayout = binding.tabLayoutLiveSection
        val tab1: TabLayout.Tab = tabLayout.newTab()
        val tab2: TabLayout.Tab = tabLayout.newTab()
        val tab3: TabLayout.Tab = tabLayout.newTab()
        val tab4: TabLayout.Tab = tabLayout.newTab()
        tab1.text = "전체"
        tab2.text = "VIDEO"
        tab3.text = "RADIO"
        tab4.text = "신입DJ"
        tabLayout.addTab(tab1)
        tabLayout.addTab(tab2)
        tabLayout.addTab(tab3)
        tabLayout.addTab(tab4)

        getLiveDataList()

//        val typeface = ResourcesCompat.getFont(this, R.font.suit_bold)
        val spannable = SpannableString(binding.announceText.text)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            5, 9,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            12, 20,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.announceText.text = spannable

        binding.scrollView.apply {
            tabLayoutSection = binding.tabLayoutLiveSection
            actionBarW = binding.actionBarW
        }

    }

    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen","android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }
    fun getNavigationBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen","android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }

    fun topBnrAutoScroll(){
        job = lifecycleScope.launch{
            delay(3500)
            binding.topbnrViewPager.setCurrentItemWithDuration(binding.topbnrViewPager.currentItem + 1, 2000)
        }
    }
    fun adBnrAutoScroll(){
        job2 = lifecycleScope.launch{
            delay(3500)
            binding.bannerViewPager.setCurrentItemWithDuration(binding.bannerViewPager.currentItem + 1, 2000)
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

    private val maxScrollY by lazy {
        binding.topbnrViewPager.height - binding.actionBarW.height
    }

    private val endAlpha = 1f

    override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
//        Log.d("max","${maxScrollY}")
        if(scrollY > CHANGE_BOUNDARY){
            underChangeBoundary()
            val alpha = when {
                scrollY >= maxScrollY -> endAlpha
                else -> (scrollY - CHANGE_BOUNDARY).toFloat() / (maxScrollY - CHANGE_BOUNDARY)
            }
            binding.actionBarW.alpha = alpha
        } else {
            overChangeBoundary()
            binding.actionBarW.alpha = endAlpha
        }


//        Log.d("ababab","${scrollY},${binding.tabLayoutLiveSection.y}, ${binding.actionBarW.height}")
//        if(scrollY > binding.tabLayoutLiveSection.y - binding.actionBarW.height){
//            binding.tabLayoutLiveSection?.translationY = scrollY - binding.tabLayoutLiveSection.top.toFloat() + binding.actionBarW.height.toFloat()
//        } else {
//            binding.tabLayoutLiveSection.translationY = 0f
//        }

    }

    fun underChangeBoundary(){
        binding.actionBarW.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.dallaLogo.visibility = View.VISIBLE
        binding.btnAlarmW.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_alarm_b))
        binding.btnMessageW.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_message_b))
        binding.btnRankingW.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_ranking_b))
        binding.btnStoreW.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_store_b))
    }
    fun overChangeBoundary(){
        binding.actionBarW.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        binding.dallaLogo.visibility = View.GONE
        binding.btnAlarmW.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_alarm_w))
        binding.btnMessageW.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_message_w))
        binding.btnRankingW.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_ranking_w))
        binding.btnStoreW.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_store_w))
    }

    private fun getTopBnrList() {
        service.getTopBnrData().enqueue(object : Callback<TopBnrDataList> {
            override fun onResponse(
                call: Call<TopBnrDataList>,
                response: Response<TopBnrDataList>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        Log.d("banner","${it.TopBannerList[0]}")
                        val bannerList = it.TopBannerList
                        topBnrLists.addAll(bannerList)
                        Log.d("bannerlist","${topBnrLists}")

                        binding.topbnrViewPager.apply {
                            adapter = TobBnrAdapter(topBnrLists)
                            setCurrentItem(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % topBnrLists.size, false)
                            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageScrollStateChanged(state: Int) {
                                    super.onPageScrollStateChanged(state)
                                    when (state) {
                                        ViewPager2.SCROLL_STATE_IDLE -> {
                                            if (!job.isActive) topBnrAutoScroll()
                                        }
                                        ViewPager2.SCROLL_STATE_DRAGGING -> job.cancel()
                                    }
                                }

                                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                                    val textView = binding.topbnrViewPager.findViewById<ConstraintLayout>(R.id.topbnrTextLayout)
                                    val itemViewBinding = ItemTopbnrBinding.inflate(layoutInflater)
                                    val topbnrTextLayout = itemViewBinding.topbnrTextLayout
                                    val text = binding.sampleTextView
////                                    Log.d("qwerasdf","${positionOffset}")
//                                    if(positionOffset < 0.5f){
//                                        val offset = positionOffset * 2
//                                        text.translationX = -offset * text.width
//                                    } else {
//                                        val offset = ( 1- positionOffset) * 2
//                                        text.translationX = offset * text.width
//                                    }
                                    val textAnimator = ObjectAnimator.ofFloat(text, "translationX", -text.width.toFloat(), 0f)
                                    textAnimator.duration = 2500
                                    textAnimator.start()
                                }
                            })

                            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
                                override fun onGlobalLayout() {
                                    CHANGE_BOUNDARY = (binding.topbnrViewPager.height * (1/2.0)).toInt()
                                    Log.d("jaja","${CHANGE_BOUNDARY}")

                                    actionBarWHeight = binding.actionBarW.height

                                    binding.topbnrViewPager.viewTreeObserver.removeOnGlobalLayoutListener(this)
                                }
                            })

                        }
                    }
                }
            }

            override fun onFailure(call: Call<TopBnrDataList>, t: Throwable) {
                Log.d("topBannerList","통신실패")
            }

        })
    }

    private fun getFollowingList(){
        service.getFollowingData().enqueue(object: Callback<FollowingDataList>{
            override fun onResponse(
                call: Call<FollowingDataList>,
                response: Response<FollowingDataList>
            ) {
                if(response.isSuccessful){
                    response.body()?.let{
//                        val followingLists = it.StarLists
                        followingList.addAll(it.StarLists)
                        Log.d("follow","2, ${followingList}")
                        binding.followingRecylcerView.apply {
                            adapter = FollowingAdapter(followingList)
                            layoutManager =
                                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<FollowingDataList>, t: Throwable) {
                Log.d("follow","${t.toString()}")
            }

        })
    }

    private fun getEvenetList(){
        service.getEventData().enqueue(object: Callback<EventBannerDataList>{
            override fun onResponse(
                call: Call<EventBannerDataList>,
                response: Response<EventBannerDataList>
            ) {
                if (response.isSuccessful){
                    response.body()?.let{

                        Log.d("eventbanner","${it}")
                        eventLists.addAll(it.event_bannerList)

                        binding.bannerViewPager.apply {
                            adapter = BannerAdapter(eventLists)
                            setCurrentItem(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % eventLists.size, false)
                            binding.bannerText.text = getString(R.string.banner_count, 1, eventLists.size)
                            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    val realPosition = position % eventLists.size
                                    binding.bannerText.text =
                                        getString(R.string.banner_count, realPosition + 1, eventLists.size)
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
                    }
                }
            }

            override fun onFailure(call: Call<EventBannerDataList>, t: Throwable) {
                Log.d("eventbanner","통신실패")
            }

        })
    }

    private fun getLiveDataList(){
        service.getLiveSectionData(RoomListRequest(PageNo = 1)).enqueue(object: Callback<LiveSectionDataList>{
            override fun onResponse(
                call: Call<LiveSectionDataList>,
                response: Response<LiveSectionDataList>
            ) {
                response.body()?.let {
                    liveSectionLists.addAll(it.RoomLists)
                    Log.d("liveSection","2, ${liveSectionLists}")

                    binding.liveSectionRecylcerView.apply {
                        adapter = LiveSectionAdapter(liveSectionLists)
                        layoutManager =
                            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                    }
                }
            }

            override fun onFailure(call: Call<LiveSectionDataList>, t: Throwable) {
                Log.d("liveSection","${t.toString()}")
            }

        })
    }

    private fun ViewPager2.setCurrentItemWithDuration(
        item: Int, duration: Long,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = width
    ){
        val pxToDrag: Int = pagePxWidth * (item - currentItem)
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0


        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val currentPxToDrag = (currentValue - previousValue).toFloat()
            fakeDragBy(-currentPxToDrag)
            previousValue = currentValue
        }

        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) { beginFakeDrag() }
            override fun onAnimationEnd(animation: Animator) { endFakeDrag() }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        animator.interpolator = interpolator
        animator.duration = duration
        animator.start()
    }
}

