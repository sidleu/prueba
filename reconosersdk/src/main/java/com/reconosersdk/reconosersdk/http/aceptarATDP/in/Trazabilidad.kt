package com.reconosersdk.reconosersdk.http.aceptarATDP.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 ** Object for the service to Trazabilidad
 */

@Parcelize
data class Trazabilidad(
    @SerializedName("city", alternate = ["City"])
    @Expose
    var city: String? = "string",
    @SerializedName("country", alternate = ["Country"])
    @Expose
    var country: String? = "string",
    @SerializedName("device", alternate = ["Device"])
    @Expose
    var device: String? = "string",
    @SerializedName("ip", alternate = ["Ip"])
    @Expose
    var ip: String? = "string",
    @SerializedName("latitud", alternate = ["Latitud"])
    @Expose
    var latitud: String? = "string",
    @SerializedName("lenguaje", alternate = ["Lenguaje"])
    @Expose
    var lenguaje: String? = "string",
    @SerializedName("longitud", alternate = ["Longitud"])
    @Expose
    var longitud: String? = "string",
    @SerializedName("navegador", alternate = ["Navegador"])
    @Expose
    var navegador: String? = "string",
    @SerializedName("postalCode", alternate = ["PostalCode"])
    @Expose
    var postalCode: String? = "string",
    @SerializedName("resolucion", alternate = ["Resolucion"])
    @Expose
    var resolucion: String? = "string",
    @SerializedName("resolucionCamera", alternate = ["ResolucionCamera"])
    @Expose
    var resolucionCamera: String? = "string",
    @SerializedName("subdivision", alternate = ["Subdivision"])
    @Expose
    var subdivision: String? = "string",
    @SerializedName("tiempo", alternate = ["Tiempo"])
    @Expose
    var tiempo: Int? = 0,
    @SerializedName("userAgent", alternate = ["UserAgent"])
    @Expose
    var userAgent: String? = "string",
    @SerializedName("versionNavegador", alternate = ["VersionNavegador"])
    @Expose
    var versionNavegador: String? = "string"
) : Parcelable {
    constructor() : this("string",  "string", "string",  "string", "string","string", "string", "string", "string","string","string", "string", 0, "string","string")
}
