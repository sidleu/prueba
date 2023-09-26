package com.reconosersdk.reconosersdk.http.consultAgreementProcess.out


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

/**
 * Object for the service to Biometrias
 */

@Parcelize
data class Biometrias(
    @SerializedName(value = "tipo", alternate = ["Tipo"])
    @Expose
    var tipo: String? = "",
    @SerializedName(value = "fuente", alternate = ["Fuente"])
    @Expose
    var fuente: String? = "",
    @SerializedName(value = "score", alternate = ["Score"])
    @Expose
    var score: Int? = 0,
    @SerializedName(value = "esValido", alternate = ["EsValido"])
    @Expose
    var esValido: Boolean? = false,
    @SerializedName(value = "fechaImagen", alternate = ["FechaImagen"])
    @Expose
    var fechaImagen: String? = "",
    @SerializedName(value = "fechaComparacion", alternate = ["FechaComparacion"])
    @Expose
    var fechaComparacion: String? = ""
) : Parcelable {
    constructor() : this("",  "", 0,  false, "", "")
}