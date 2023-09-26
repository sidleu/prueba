package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.openSource


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ValParametro(
    @SerializedName("nombreParametro")
    var nombreParametro: String? = "",
    @SerializedName("score")
    var score: Int? = 0,
    @SerializedName("validacionOk")
    var validacionOk: Boolean? = false
): Parcelable