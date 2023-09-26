package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.openSource


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataOpenSourceValidate(
    @SerializedName("valFuente")
    var valFuente: List<ValFuente>? = listOf(),
    @SerializedName("validacionOk")
    var validacionOk: Boolean? = false
): Parcelable