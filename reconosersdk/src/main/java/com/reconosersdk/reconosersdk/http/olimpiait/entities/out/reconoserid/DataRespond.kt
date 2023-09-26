package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataRespond(
    @SerializedName("resultados", alternate = ["Code"])
    var resultados: MutableList<Resultado>? = mutableListOf()
): Parcelable