package com.reconosersdk.reconosersdk.http.saveDocumentSides.`in`


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

/**
 * Object for the service to LogOcrReverso
 */


@Parcelize
data class LogOcrReverso(
    @SerializedName(value = "ciudadanoGuid", alternate = ["CiudadanoGuid"])
    @Expose
    var ciudadanoGuid: String? = "",
    @SerializedName(value = "numeroDocumento", alternate = ["NumeroDocumento"])
    @Expose
    var numeroDocumento: String? = "",
    @SerializedName(value = "primerNombre", alternate = ["PrimerNombre"])
    @Expose
    var primerNombre: String? = "",
    @SerializedName(value = "segundoNombre", alternate = ["SegundoNombre"])
    @Expose
    var segundoNombre: String? = "",
    @SerializedName(value = "primerApellido", alternate = ["PrimerApellido"])
    @Expose
    var primerApellido: String? = "",
    @SerializedName(value = "segundoApellido", alternate = ["SegundoApellido"])
    @Expose
    var segundoApellido: String? = "",
    @SerializedName(value = "sexo", alternate = ["Sexo"])
    @Expose
    var sexo: String? = "",
    @SerializedName(value = "rh", alternate = ["Rh"])
    @Expose
    var rh: String? = "",
    @SerializedName(value = "fechaNacimiento", alternate = ["FechaNacimiento"])
    @Expose
    var fechaNacimiento: String? = "",
    @SerializedName(value = "fechaExpedicionDoc", alternate = ["FechaExpedicionDoc"])
    @Expose
    var fechaExpedicionDoc: String? = ""
) : Parcelable {
    constructor() : this("",   "",  "",
        "", "", "", "", "", "",
        "")
}