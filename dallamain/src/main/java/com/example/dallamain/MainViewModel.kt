package com.example.dallamain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dallamain.ApiService.RetrofitService
import com.example.dallamain.Data.*
import com.example.dallamain.Manager.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val service: RetrofitService = RetrofitManager.retrofit.create(RetrofitService::class.java)

    init {
        getTopBnrLists()
        getFollowingList()
        getEventList()
        getLiveDataList()
    }

    // top bnr
    private var _topBnrLists = MutableLiveData<ArrayList<TopBnrData>>()
    val topBnrLists: LiveData<ArrayList<TopBnrData>> = _topBnrLists

    // follwing list
    private var _followingList = MutableLiveData<ArrayList<FollowingData>>()
    val followingList: LiveData<ArrayList<FollowingData>> = _followingList

    // eventLists
    private var _eventLists = MutableLiveData<ArrayList<EventBannerData>>()
    val eventLists: LiveData<ArrayList<EventBannerData>> = _eventLists

    // live section
    private var _liveSectionLists = MutableLiveData<ArrayList<LiveSectionData>>()
    val liveSectionLists: LiveData<ArrayList<LiveSectionData>> = _liveSectionLists

    // bj textview boolean
    private var _bjText = MutableLiveData<Boolean>(true)
    val bjText: LiveData<Boolean> = _bjText

    // fan textview boolean
    private var _fanText = MutableLiveData<Boolean>(false)
    val fanText: LiveData<Boolean> = _fanText

    // team textview boolean
    private var _teamText = MutableLiveData<Boolean>(false)
    val teamText: LiveData<Boolean> = _teamText



    fun getTopBnrLists(){
        service.getTopBnrData().enqueue(object: Callback<TopBnrDataList> {
            override fun onResponse( call: Call<TopBnrDataList>, response: Response<TopBnrDataList>) {
                if(response.isSuccessful){
                    response.body()?.let{
                        _topBnrLists.value = it.TopBannerList
                    }
                }
            }

            override fun onFailure(call: Call<TopBnrDataList>, t: Throwable) {
                Log.d("viewbannerlist","${t.toString()}")
            }

        })
    }

    fun getFollowingList(){
        service.getFollowingData().enqueue(object: Callback<FollowingDataList> {
            override fun onResponse( call: Call<FollowingDataList>, response: Response<FollowingDataList>) {
                if(response.isSuccessful){
                    response.body()?.let{
                        _followingList.value = it.StarLists
                    }
                }
            }

            override fun onFailure(call: Call<FollowingDataList>, t: Throwable) {
                Log.d("getFollowingList()","${t.toString()}")
            }

        })
    }

    fun getEventList(){
        service.getEventData().enqueue(object: Callback<EventBannerDataList>{
            override fun onResponse(call: Call<EventBannerDataList>, response: Response<EventBannerDataList>) {
                if(response.isSuccessful){
                    response.body()?.let{
                        _eventLists.value = it.event_bannerList
                    }
                }
            }

            override fun onFailure(call: Call<EventBannerDataList>, t: Throwable) {
                Log.d("getEventList()","${t.toString()}")
            }

        })
    }

    fun getLiveDataList(){
        service.getLiveSectionData(RoomListRequest(PageNo = 1)).enqueue(object: Callback<LiveSectionDataList>{
            override fun onResponse(call: Call<LiveSectionDataList>, response: Response<LiveSectionDataList>) {
                if(response.isSuccessful){
                    response.body()?.let{
                        _liveSectionLists.value = it.RoomLists
                    }
                }
            }

            override fun onFailure(call: Call<LiveSectionDataList>, t: Throwable) {
                Log.d("getLiveDataList()","${t.toString()}")
            }

        })
    }

    fun onTextBJClicked(){
        if(_bjText.value == true) return
        _bjText.value = true
        _fanText.value = false
        _teamText.value = false
    }
    fun onTextFanClicked(){
        if(_fanText.value == true) return
        _bjText.value = false
        _fanText.value = true
        _teamText.value = false
    }
    fun onTextTeamClicked(){
        if(_teamText.value == true) return
        _bjText.value = false
        _fanText.value = false
        _teamText.value = true
    }


}
