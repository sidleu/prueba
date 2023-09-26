package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savelogs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LogSaveBarcode(
    @SerializedName("ciudadanoGuid", alternate = ["CiudadanoGuid"])
    var ciudadanoGuid: String? = "",
    @SerializedName("fechaExpedicionDoc", alternate = ["FechaExpedicionDoc"])
    var fechaExpedicionDoc: String? = "1900-01-01",
    @SerializedName("fechaNacimiento", alternate = ["FechaNacimiento"])
    var fechaNacimiento: String? = "",
    @SerializedName("numeroDocumento", alternate = ["NumeroDocumento"])
    var numeroDocumento: String? = "",
    @SerializedName("primerApellido", alternate = ["PrimerApellido"])
    var primerApellido: String? = "",
    @SerializedName("primerNombre", alternate = ["PrimerNombre"])
    var primerNombre: String? = "",
    @SerializedName("procesoConvenioGuid", alternate = ["ProcesoConvenioGuid"])
    var procesoConvenioGuid: String? = "",
    @SerializedName("rh", alternate = ["Rh"])
    var rh: String? = "",
    @SerializedName("segundoApellido", alternate = ["SegundoApellido"])
    var segundoApellido: String? = "",
    @SerializedName("segundoNombre", alternate = ["SegundoNombre"])
    var segundoNombre: String? = "",
    @SerializedName("tipoDocumento", alternate = ["TipoDocumento"])
    var tipoDocumento: String? = "",
    @SerializedName("sexo", alternate = ["Sexo"])
    var sexo: String? = ""
) : Parcelable {
    constructor() : this("",  "", "", "", "", "", "", "",
        "","","","")
}