package com.reconosersdk.reconosersdk.http.openSource.`in`

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to ConsultarFuentesAbiertasIn
 */

@Parcelize
data class ConsultarFuentesAbiertasIn(
    @SerializedName(value = "GuidConv", alternate = ["guidConv"])
    var guidConv: String? = "",

    @SerializedName(value = "FuentesAbiertasGuid", alternate = ["fuentesAbiertasGuid"])
    var fuentesAbiertasGuid: String? = "",


    @SerializedName(value = "TipoDoc", alternate = ["tipoDoc"])
    var tipoDoc: String? = "",

    @SerializedName(value = "Documento", alternate = ["documento"])
    var documento: String? = "",

    @SerializedName(value = "Robot", alternate = ["robot"])
    var robot: List<String?> = emptyList(),

    @SerializedName(value = "Lists", alternate = ["lists"])
    var lists: List<String?> = emptyList(),

    @SerializedName(value = "Ip", alternate = ["ip"])
    var ip: String? = "",

    @SerializedName(value = "Usuario", alternate = ["usuario"])
    var usuario: String? = "",

    @SerializedName(value = "Clave", alternate = ["clave"])
    var clave: String? = ""
): Parcelable
