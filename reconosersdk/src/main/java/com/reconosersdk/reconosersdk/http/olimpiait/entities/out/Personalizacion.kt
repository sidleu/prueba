package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Personalizacion(
    @SerializedName("nombre")
    @Expose
    val nombre: String? = "SoloRostroVivo",
    @SerializedName("valor")
    @Expose
    val valor: String? = "0"
)