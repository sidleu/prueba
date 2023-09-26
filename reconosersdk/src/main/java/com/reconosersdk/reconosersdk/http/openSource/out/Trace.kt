package com.reconosersdk.reconosersdk.http.openSource.out

import com.google.gson.annotations.SerializedName

data class Trace(
    @SerializedName("Errors")
    val errors: List<String>? = listOf(),
    @SerializedName("Name")
    val name: String? = ""
)