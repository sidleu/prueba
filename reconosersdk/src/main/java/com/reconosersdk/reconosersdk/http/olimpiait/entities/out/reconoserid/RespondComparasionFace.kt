package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid


import com.google.gson.annotations.SerializedName

data class RespondComparasionFace(
    @SerializedName("code")
    val code: Int,
    @SerializedName("codeName")
    val codeName: String,
    @SerializedName("data")
    val data: Data
)