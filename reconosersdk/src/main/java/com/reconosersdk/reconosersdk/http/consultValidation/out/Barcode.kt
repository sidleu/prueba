package com.reconosersdk.reconosersdk.http.consultValidation.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Barcode
 */

@Parcelize
data class Barcode(
    @SerializedName(value = "fechaExpedicion", alternate = ["FechaExpedicion"])
    @Expose
    var  fechaExpedicion: String? = "",
    @SerializedName(value = "fechaNacimiento", alternate = ["FechaNacimiento"])
    @Expose
    var  fechaNacimiento: String? = "",
    @SerializedName(value = "numDoc", alternate = ["NumDoc"])
    @Expose
    var  numDoc: String? = "",
    @SerializedName(value = "primerApellido", alternate = ["PrimerApellido"])
    @Expose
    var  primerApellido: String? = "",
    @SerializedName(value = "primerNombre", alternate = ["PrimerNombre"])
    @Expose
    var  primerNombre: String? = "",
    @SerializedName(value = "rh", alternate = ["Rh"])
    @Expose
    var  rh: String? = "",
    @SerializedName(value = "segundoApellido", alternate = ["SegundoApellido"])
    @Expose
    var  segundoApellido: String? = "",
    @SerializedName(value = "segundoNombre", alternate = ["SegundoNombre"])
    @Expose
    var  segundoNombre: String? = "",
    @SerializedName(value = "sexo", alternate = ["Sexo"])
    @Expose
    var  sexo: String? = "",
    @SerializedName(value = "tipoDoc", alternate = ["TipoDoc"])
    @Expose
    var  tipoDoc: String? = ""
) : Parcelable {
    constructor() : this("",  "", "",  "", "",
        "", "",  "", "", "")
}