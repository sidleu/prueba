package com.reconosersdk.reconosersdk.http.openSource.out

import com.google.gson.annotations.SerializedName
import java.util.*

data class ParametroFuenteOut(
        @SerializedName("code", alternate = ["Code"])
        var code: Int? = 0,
        @SerializedName("codeName", alternate = ["CodeName"])
        val codeName: String? = "",
        @SerializedName("data", alternate = ["Data"])
        val data: List<DataParametroFuente>? = emptyList()
){
    constructor() : this(0, "", Collections.emptyList())
}