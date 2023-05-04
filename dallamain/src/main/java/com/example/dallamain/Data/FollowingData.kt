package com.example.dallamain.Data

import com.google.gson.annotations.SerializedName

data class FollowingData(
    @SerializedName("profImg") val profImg: ProfImagData,
    @SerializedName("nickNm") val text: String,
    @SerializedName("roomYn") val isBroadcasting: String,
    )

data class ProfImagData(
    @SerializedName("url") val url: String,
    @SerializedName("path") val path: String
)

data class FollowingDataList(
    @SerializedName("StarList") val StarLists: ArrayList<FollowingData>
)
