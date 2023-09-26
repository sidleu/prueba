package com.reconosersdk.reconosersdk.http.consultValidation.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Object for the service to Servicio
 */

@Parcelize
data class Servicio(
    @SerializedName(value = "barcode", alternate = ["Barcode"])
    @Expose
    var barcode: Barcode? = Barcode(),
    @SerializedName(value = "documentIsValid", alternate = ["DocumentIsValid"])
    @Expose
    var documentIsValid:  Boolean? = false,
    @SerializedName(value = "fecha", alternate = ["Fecha"])
    @Expose
    var fecha: String? = "",
    @SerializedName(value = "score", alternate = ["Score"])
    @Expose
    var score: Int? = 0,
    @SerializedName(value = "servicio", alternate = ["Servicio"])
    @Expose
    var servicio: String? = "",
    @SerializedName(value = "subTipos", alternate = ["SubTipos"])
    @Expose
    var subTipos: @RawValue Any? = Any(),
    @SerializedName(value = "terminado", alternate = ["Terminado"])
    @Expose
    var terminado: Boolean? = false,
    @SerializedName(value = "tipo", alternate = ["Tipo"])
    @Expose
    var tipo: String? = ""
): Parcelable {
    constructor() : this(Barcode(),  false,"", 0,"",  Any(), false,"")
}