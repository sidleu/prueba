package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savetraceability

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 ** Object for the service to Trazabilidad
 */
@Parcelize
data class Trazabilidad(
    @SerializedName("device", alternate = ["Device"])
    var device: String? = "",
    @SerializedName("ip", alternate = ["Ip"])
    var ip: String? = "",
    @SerializedName("latitud", alternate = ["Latitud"])
    var latitud: String? = "",
    @SerializedName("lenguaje", alternate = ["Lenguaje"])
    var lenguaje: String? = "",
    @SerializedName("longitud", alternate = ["Longitud"])
    var longitud: String? = "",
    @SerializedName("navegador", alternate = ["Navegador"])
    var navegador: String? = "",
    @SerializedName("resolucion", alternate = ["Resolucion"])
    var resolucion: String? = "",
    @SerializedName("resolucionCamera", alternate = ["ResolucionCamera"])
    var resolucionCamera: String? = "",
    @SerializedName("tiempo", alternate = ["Tiempo"])
    var tiempo: Int? = 0,
    @SerializedName("userAgent", alternate = ["UserAgent"])
    var userAgent: String? = "",
    @SerializedName("versionNavegador", alternate = ["VersionNavegador"])
    var versionNavegador: String? = ""
) : Parcelable {
    constructor() : this("",  "", "",  "", "","", "", "", 0, "","")
}