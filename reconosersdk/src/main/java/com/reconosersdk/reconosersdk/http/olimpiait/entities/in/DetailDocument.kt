package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailDocument(
    @SerializedName("typeDoc")
    @Expose
    val typeDoc: String? = "",

    @SerializedName("numDoc")
    @Expose
    val numDoc: String? = ""
) {
    constructor(): this ("", "")
}