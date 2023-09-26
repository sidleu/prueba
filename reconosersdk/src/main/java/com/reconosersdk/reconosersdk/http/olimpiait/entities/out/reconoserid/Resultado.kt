package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Resultado(
    @SerializedName("esValido", alternate = ["EsValido"])
    val esValido: Boolean = false,
    @SerializedName("fechaImagen", alternate = ["FechaImagen"])
    val fechaImagen: String = "",
    @SerializedName("formato", alternate = ["Formato"])
    val formato: String = "",
    @SerializedName("fuente", alternate = ["Fuente"])
    val fuente: String = "",
    @SerializedName("resultado", alternate = ["Resultado"])
    val resultado: String = "",
    @SerializedName("score", alternate = ["Score"])
    val score: Double = 0.0
): Parcelable