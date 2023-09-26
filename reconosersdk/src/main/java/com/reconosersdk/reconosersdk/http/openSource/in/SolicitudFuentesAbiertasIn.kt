package com.reconosersdk.reconosersdk.http.openSource.`in`

import com.google.gson.annotations.SerializedName

data class SolicitudFuentesAbiertasIn(
    @SerializedName(value = "guidConv", alternate = ["GuidConv"])
    var guidConv: String?,

    @SerializedName(value = "tipoDoc", alternate = ["TipoDoc"])
    var tipoDoc: String?,

    @SerializedName(value = "documento", alternate = ["Documento"])
    var documento: String?,

    @SerializedName(value = "Robot", alternate = ["robot"])
    var robot: List<String?>,

    @SerializedName(value = "Lists", alternate = ["lists"])
    var lists: List<String?>,

    @SerializedName(value = "ip", alternate = ["Ip"])
    var ip: String?,

    @SerializedName(value = "usuario", alternate = ["Usuario"])
    var usuario: String?,

    @SerializedName(value = "clave", alternate = ["Clave"])
    var clave: String?
) {
    constructor() : this("",  "", "",  emptyList(), emptyList(),"", "", "")
}
