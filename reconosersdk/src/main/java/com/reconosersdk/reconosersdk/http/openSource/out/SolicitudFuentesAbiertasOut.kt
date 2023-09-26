package com.reconosersdk.reconosersdk.http.openSource.out

import com.google.gson.annotations.SerializedName

data class SolicitudFuentesAbiertasOut(
    @SerializedName("code", alternate = ["Code"])
    var code: Int? = 0,
    @SerializedName("codeName", alternate = ["CodeName"])
    val codeName: String? = "",
    @SerializedName("data", alternate = ["Data"])
    val data: DataSolicitud? = DataSolicitud()
)