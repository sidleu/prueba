package com.reconosersdk.reconosersdk.http.aceptarATDP.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 ** Object for the service to AceptarAtdpIn
 */

@Parcelize
data class AceptarAtdpIn(
    @SerializedName(value = "aceptar", alternate = ["Aceptar"])
    @Expose
    var aceptar: Boolean?  = true,
    @SerializedName(value = "guidProcesoConvenio", alternate = ["GuidProcesoConvenio"])
    @Expose
    var guidProcesoConvenio: String? = "",
    @SerializedName(value = "trazabilidad", alternate = ["com.reconosersdk.reconosersdk.http.aceptarATDP.`in`.Trazabilidad"])
    @Expose
    var trazabilidad: Trazabilidad? = Trazabilidad(),
    @SerializedName(value = "validadorWeb", alternate = ["ValidadorWeb"])
    @Expose
    var validadorWeb: Boolean? = true
): Parcelable {
    constructor() : this(true,  "", Trazabilidad(), true)
}