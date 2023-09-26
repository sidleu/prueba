package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("esValido")
    val esValido: Boolean,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("resultado")
    val resultado: String,
    @SerializedName("score")
    val score: Double
)