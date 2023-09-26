package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to ValidarBiometriaIn
 */
@Parcelize
data class ValidarBiometriaIn (
    @SerializedName(value = "GuidCiudadano", alternate = ["guidCiudadano"])
    @Expose
    var guidCiudadano: String? = "",

    @SerializedName(value = "GuidProcesoConvenio", alternate = ["guidProcesoConvenio"])
    @Expose
    var guidProcesoConvenio: String? = "",

    @SerializedName(value = "IdServicio", alternate = ["idServicio"])
    @Expose
    var idServicio: Int? = 0,

    @SerializedName("subTipo")
    @Expose
    var subTipo: String? = "",

    @SerializedName(value = "Biometria", alternate = ["biometria"])
    @Expose
    var biometria: String? = "",

    @SerializedName(value = "Formato", alternate = ["formato"])
    @Expose
    var formato: String? = ""
) : Parcelable {
    constructor() : this("", "", 0, "","", "")
}