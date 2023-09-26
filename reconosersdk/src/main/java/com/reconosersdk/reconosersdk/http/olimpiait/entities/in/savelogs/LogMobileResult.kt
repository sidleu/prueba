package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savelogs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LogMobileResult(
    @SerializedName("analisisDocumento", alternate = ["AnalisisDocumento"])
    val analisisDocumento: Double? = 0.0,
    @SerializedName("analisisFacial", alternate = ["AnalisisFacial"])
    val analisisFacial: Double? = 0.0,
    @SerializedName("encontradoBD", alternate = ["EncontradoBD"])
    val encontradoBD: Boolean = false,
    @SerializedName("objetoAnalisis", alternate = ["ObjetoAnalisis"])
    val objetoAnalisis: String? = "",
    @SerializedName("procesoConvenioGuid", alternate = ["ProcesoConvenioGuid"])
    val procesoConvenioGuid: String? = "",
    @SerializedName("puntajeTotal", alternate = ["PuntajeTotal"])
    val puntajeTotal: Double? = 0.0
) : Parcelable {
    constructor() : this(0.0, 0.0, false, "", "", 0.0)
}