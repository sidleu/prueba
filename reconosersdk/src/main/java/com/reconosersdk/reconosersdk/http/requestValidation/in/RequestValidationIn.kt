package com.reconosersdk.reconosersdk.http.requestValidation.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to RequestValidationIn
 */

@Parcelize
data class RequestValidationIn(
    @SerializedName(value = "infCandidato", alternate = ["InfCandidato"])
    @Expose
    var infCandidato: String? = "",
    @SerializedName(value = "asesor", alternate = ["Asesor"])
    @Expose
    var asesor: String? = "",
    @SerializedName(value = "celular", alternate = ["Celular"])
    @Expose
    var celular: String? = "",
    @SerializedName(value = "ciudadanoData", alternate = ["CiudadanoData"])
    @Expose
    var ciudadanoData: CiudadanoData? = CiudadanoData(),
    @SerializedName(value = "clave", alternate = ["Clave"])
    @Expose
    var clave: String? = "",
    @SerializedName(value = "consultaFuentes", alternate = ["ConsultaFuentes"])
    @Expose
    var consultaFuentes: Boolean? = false,
    @SerializedName(value = "email", alternate = ["Email"])
    @Expose
    var email: String? = "",
    @SerializedName(value = "guidConv", alternate = ["GuidConv"])
    @Expose
    var guidConv: String? = "",
    @SerializedName(value = "numDoc", alternate = ["NumDoc"])
    @Expose
    var numDoc: String? = "",
    @SerializedName(value = "prefCelular", alternate = ["PrefCelular"])
    @Expose
    var prefCelular: String? = "",
    @SerializedName(value = "procesoWhatsapp", alternate = ["ProcesoWhatsapp"])
    @Expose
    var procesoWhatsapp: Boolean? = false,
    @SerializedName(value = "sede", alternate = ["Sede"])
    @Expose
    var sede: String? = "",
    @SerializedName(value = "tipoDoc", alternate = ["TipoDoc"])
    @Expose
    var tipoDoc: String? = "",
    @SerializedName(value = "tipoValidacion", alternate = ["TipoValidacion"])
    @Expose
    var tipoValidacion: Int? = 0,
    @SerializedName(value = "usuario", alternate = ["Usuario"])
    @Expose
    var usuario: String? = ""
) : Parcelable {
    constructor() : this(
        "", "", "", CiudadanoData(),
        "", false, "", "", "", "",
        false, "", "", 0, ""
    )
}