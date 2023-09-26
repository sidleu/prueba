package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 ** Object for the service to EnviarOTPIn
 */
@Parcelize
data class EnviarOTPIn(
    @SerializedName(value = "guidCiudadano", alternate = ["GuidCiudadano"])
    @Expose
    var guidCiudadano: String? = "",

    @SerializedName(value = "guidProcesoConvenio", alternate = ["GuidProcesoConvenio"])
    @Expose
    var guidProcesoConvenio: String? = "",

    @SerializedName(value = "datosOTP", alternate = ["DatosOTP"])
    @Expose
    var datosOTP: DatosOTP? = DatosOTP()
) : Parcelable {
    constructor() : this("", "", DatosOTP())
}