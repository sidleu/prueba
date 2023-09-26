package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListaImagenes(
    @SerializedName("fecha", alternate = ["Fecha"])
    val fecha: String = "",
    @SerializedName("fuente", alternate = ["Fuente"])
    val fuente: String = ""
) : Parcelable