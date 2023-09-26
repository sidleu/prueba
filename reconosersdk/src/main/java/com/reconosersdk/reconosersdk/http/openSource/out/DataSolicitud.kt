package com.reconosersdk.reconosersdk.http.openSource.out

import com.google.gson.annotations.SerializedName
import java.util.*

data class DataSolicitud(
    @SerializedName("transaccionGuid", alternate = ["TransaccionGuid"])
    val transaccionGuid: String? = "",
    @SerializedName("ejecucionCorrecta", alternate = ["EjecucionCorrecta"])
    val ejecucionCorrecta: Boolean? = false,
    @SerializedName("inner", alternate = ["Inner"])
    val inner: String? = "",
    @SerializedName("mesage", alternate = ["Mesage"])
    var mesage: String? = "",
    @SerializedName("trace", alternate = ["Trace"])
    val trace: List<Trace>? = emptyList(),
    @SerializedName("error", alternate = ["Error"])
    val error: List<Error>? = emptyList()
) {
    constructor() : this("", false, "", "", emptyList(), emptyList())
}