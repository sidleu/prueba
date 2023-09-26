package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`


import com.google.gson.annotations.SerializedName

data class OpenSourceValidationRequest(
    @SerializedName("procesoConvenioGuid")
    var procesoConvenioGuid: String? = "",
    @SerializedName("tipoValidacion")
    var tipoValidacion: Int? = 0
)