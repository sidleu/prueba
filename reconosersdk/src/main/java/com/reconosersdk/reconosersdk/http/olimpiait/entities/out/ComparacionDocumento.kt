package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to ComparacionDocumento
 */
@Parcelize
data class ComparacionDocumento(
    @SerializedName(value = "mensaje", alternate = ["Mensaje"])
    @Expose
    val mensaje: String? = "",
    @SerializedName(value = "porcentajeValidacionANI", alternate = ["PorcentajeValidacionANI"])
    @Expose
    val porcentajeValidacionANI: Int? = 0,
    @SerializedName(value = "porcentajeValidacionBarcode", alternate = ["PorcentajeValidacionBarcode"])
    @Expose
    val porcentajeValidacionBarcode: Int? = 0
):  Parcelable {
    constructor() : this( "", 0, 0)
}
