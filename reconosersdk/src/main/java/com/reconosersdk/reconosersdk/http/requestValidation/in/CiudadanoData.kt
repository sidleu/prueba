package com.reconosersdk.reconosersdk.http.requestValidation.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to CiudadanoData
 */

@Parcelize
data class CiudadanoData(
    @SerializedName(value = "fechaExpDoc", alternate = ["FechaExpDoc"])
    @Expose
    var fechaExpDoc: String? = "",
    @SerializedName(value = "fechaNac", alternate = ["FechaNac"])
    @Expose
    var fechaNac: String? = "",
    @SerializedName(value = "primerApellido", alternate = ["PrimerApellido"])
    @Expose
    var primerApellido: String? = "",
    @SerializedName(value = "primerNombre", alternate = ["PrimerNombre"])
    @Expose
    var primerNombre: String? = "",
    @SerializedName(value = "segundoApellido", alternate = ["SegundoApellido"])
    @Expose
    var segundoApellido: String? = "",
    @SerializedName(value = "segundoNombre", alternate = ["SegundoNombre"])
    @Expose
    var segundoNombre: String? = ""
): Parcelable {
    constructor() : this("",  "", "", "", "", "")
}