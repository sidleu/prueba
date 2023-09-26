package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.finish

import com.google.gson.annotations.SerializedName

data class RespondFinishProcess(
    @SerializedName("code", alternate = ["Code"])
    var code: Int? = 0,
    @SerializedName("codeName", alternate = ["CodeName"])
    var codeName: String? = "",
    @SerializedName("data")
    var data: DataFail? = DataFail(),
    @SerializedName("Data")
    var dataError: DataError? = DataError()
)
