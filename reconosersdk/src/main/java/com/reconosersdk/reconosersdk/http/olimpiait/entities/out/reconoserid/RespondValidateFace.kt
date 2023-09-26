package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RespondValidateFace(
    @SerializedName("code", alternate = ["Code"])
    val code: Int,
    @SerializedName("codeName",  alternate = ["Codename"])
    val codeName: String,
    @SerializedName("data")
    var data: DataRespond
): Parcelable