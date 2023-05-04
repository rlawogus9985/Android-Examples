package com.example.dallamain.Data

import com.google.gson.annotations.SerializedName

data class LiveSectionData(
    @SerializedName("bjProfileImage") val profileImage: String,
    @SerializedName("roomTitle") val liveTitleText: String,
    @SerializedName("teamBgUrl") val medalImage: String?,
    @SerializedName("bjMemSex") val genderImage: String,
    @SerializedName("bjNickName") val djName: String,
    @SerializedName("countLiveMem") val peopleCount: String,
    @SerializedName("countByeol") val likeCount: String,
    @SerializedName("risingYn") val risingYn: String,
    val bdgImage1: Int?,
    val bdgImage2: Int?
)

data class LiveSectionDataList(
    @SerializedName("RoomList") val RoomLists: ArrayList<LiveSectionData>
)
