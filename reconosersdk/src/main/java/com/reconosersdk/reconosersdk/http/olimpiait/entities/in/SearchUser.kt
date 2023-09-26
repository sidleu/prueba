package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import com.google.gson.annotations.SerializedName

data class SearchUser(
    @SerializedName("guidConvenio")
    val guidConvenio: String? = "",
    @SerializedName("numDocumento")
    val numDocument: String? = "",
    @SerializedName("codigoPais")
    val countryCode: String? = "",
    @SerializedName("tipoDocumento")
    var documentType: String? = ""
)