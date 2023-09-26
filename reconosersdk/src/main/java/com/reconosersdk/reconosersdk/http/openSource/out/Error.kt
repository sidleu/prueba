package com.reconosersdk.reconosersdk.http.openSource.out

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("codigo")
    val codigo: String? = "",
    @SerializedName("descripcion")
    val descripcion: String? = ""
){
    constructor(): this("", "")
}