package com.reconosersdk.reconosersdk.http.openSource.out

import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.validateine.out.IpGeo
import com.reconosersdk.reconosersdk.http.validateine.out.IpScam
import java.util.*

data class DataParametroFuente(
        @SerializedName("id", alternate = ["Id"])
        val id: Int? = 0,
        @SerializedName("nombre", alternate = ["Nombre"])
        val nombre: String? = "",
        @SerializedName("descripcion", alternate = ["Descripcion"])
        val descripcion: String? = "",
        @SerializedName("riesgo", alternate = ["Riesgo"])
        val riesgo: Boolean? = false,
        @SerializedName("pais", alternate = ["Pais"])
        val pais: String? = "",
        @SerializedName("tipo", alternate = ["Tipo"])
        val tipo: String? = "",
) {
    constructor() : this(0, "", "", false,"", "", )
}