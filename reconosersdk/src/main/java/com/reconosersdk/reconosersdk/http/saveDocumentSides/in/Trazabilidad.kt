package com.reconosersdk.reconosersdk.http.saveDocumentSides.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Trazabilidad
 */

@Parcelize
data class Trazabilidad(
    @SerializedName("device", alternate = ["Device"])
    @Expose
    var  device: String? = "",
    @SerializedName("ip", alternate = ["Ip"])
    @Expose
    var  ip: String? = "",
    @SerializedName("navegador", alternate = ["Navegador"])
    @Expose
    var  navegador: String? = "",
    @SerializedName("resolucion", alternate = ["Resolucion"])
    @Expose
    var  resolucion: String? = "",
    @SerializedName("tiempo", alternate = ["Tiempo"])
    @Expose
    var  tiempo: Int? = 0,
    @SerializedName("userAgent", alternate = ["UserAgent"])
    @Expose
    var  userAgent: String? = "",
    @SerializedName("versionNavegador", alternate = ["VersionNavegador"])
    @Expose
    var  versionNavegador: String? = ""
) : Parcelable {
    constructor() : this("",  "", "",  "", 0,"", "")
}
