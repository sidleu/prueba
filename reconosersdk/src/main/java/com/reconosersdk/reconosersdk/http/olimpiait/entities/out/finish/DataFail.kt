package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.finish

import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.ErrorEntransaccion

data class DataFail(
    @SerializedName("errorEntransaccion", alternate = ["ErrorEntransaccion"])
    var errorEntransaccion: List<ErrorEntransaccion> = listOf(),
    @SerializedName("esExitosa", alternate = ["EsExitosa"])
    var esExitosa: Boolean = false
)
