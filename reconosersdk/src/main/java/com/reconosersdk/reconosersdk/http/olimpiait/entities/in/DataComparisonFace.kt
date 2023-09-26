package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import com.google.gson.annotations.SerializedName

data class DataComparisonFace(
    @SerializedName(value = "guidConvenio", alternate = ["GuidConvenio"])
    val guidConvenio: String,
    @SerializedName(value = "rostro1", alternate = ["Rostro1"])
    val rostro1: String,
    @SerializedName(value = "formato1", alternate = ["Formato1"])
    val formato1: String,
    @SerializedName(value = "rostro2", alternate = ["Rostro2"])
    val rostro2: String,
    @SerializedName(value = "formato2", alternate = ["Formato2"])
    val formato2: String,
    @SerializedName(value = "ciudadanoGuid")
    val ciudadanoGuid: String,
    @SerializedName(value = "procesoConvenioGuid")
    val procesoConvenioGuid: String
)
