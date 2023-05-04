package com.example.dallamain.Data

import com.google.gson.annotations.SerializedName

data class TopBnrData (
    val image_profile: String,
    @SerializedName("mem_nick") val mem_nickname: String,
    val title: String,
    @SerializedName("badgeSpecial") val isBdg: Int
    )

data class TopBnrDataList(
    @SerializedName("BannerList") val TopBannerList: ArrayList<TopBnrData>
    )