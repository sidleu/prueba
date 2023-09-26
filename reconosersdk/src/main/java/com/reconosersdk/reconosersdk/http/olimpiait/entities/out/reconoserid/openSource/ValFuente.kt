package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.openSource


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ValFuente(
    @SerializedName("codigoFuente")
    var codigoFuente: String? = "",
    @SerializedName("valParametro")
    var valParametro: List<ValParametro>? = listOf(),
    @SerializedName("validacionOk")
    var validacionOk: Boolean? = false
): Parcelable