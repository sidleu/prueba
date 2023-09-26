package com.reconosersdk.reconosersdk.http.consultValidation.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to ConsultValidationIn
 */

@Parcelize
data class ConsultValidationIn(
    @SerializedName(value = "clave", alternate = ["Clave"])
    @Expose
    var clave: String? = "",
    @SerializedName(value = "codigoCliente", alternate = ["CodigoCliente"])
    @Expose
    var codigoCliente: String? = "",
    @SerializedName(value = "guidConv", alternate = ["GuidConv"])
    @Expose
    var guidConv: String? = "",
    @SerializedName(value = "procesoConvenioGuid", alternate = ["ProcesoConvenioGuid"])
    @Expose
    var procesoConvenioGuid: String? = "",
    @SerializedName(value = "usuario", alternate = ["Usuario"])
    @Expose
    var usuario: String? = ""
): Parcelable {
    constructor() : this("",  "", "",  "", "")
}