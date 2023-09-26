package com.reconosersdk.reconosersdk.http.openSource.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Fuentes
 */

@Parcelize
data class Fuentes(
    @SerializedName(value = "codigo", alternate = ["Codigo"])
    @Expose
    var  codigo: String? = "",

    @SerializedName(value = "nombre", alternate = ["Nombre"])
    @Expose
    var  nombre: String? = "",

    @SerializedName(value = "mensaje", alternate = ["Mensaje"])
    @Expose
    var  mensaje: String? = "",

    @SerializedName(value = "riesgo", alternate = ["Riesgo"])
    @Expose
    var  riesgo: Boolean? = false,

    @SerializedName(value = "score", alternate = ["Score"])
    @Expose
    var  score: Int? = 0
) : Parcelable {
    constructor() : this("",  "", "",  false, 0)
}