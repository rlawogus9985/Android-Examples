package com.example.dallamain.ApiService

import com.example.dallamain.Data.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET("RqBannerList")
    fun getTopBnrData(): Call<TopBnrDataList>

    @GET("RqMyStarList")
    fun getFollowingData(): Call<FollowingDataList>

    @GET("RqEventBannerList")
    fun getEventData(): Call<EventBannerDataList>

    @POST("RqRoomList")
    fun getLiveSectionData(
        @Body request: RoomListRequest
    ): Call<LiveSectionDataList>

}