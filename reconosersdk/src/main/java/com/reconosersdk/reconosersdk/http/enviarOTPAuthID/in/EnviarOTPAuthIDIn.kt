package com.reconosersdk.reconosersdk.http.enviarOTPAuthID.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.DatosOTP
import kotlinx.android.parcel.Parcelize

/**
 ** Object for the service to EnviarOTPAuthIDIn
 */

@Parcelize
data class EnviarOTPAuthIDIn(
    @SerializedName(value = "correo", alternate = ["Correo"])
    @Expose
    var correo: String? = "",
    @SerializedName(value = "datosOTP", alternate = ["DatosOTP"])
    @Expose
    var datosOTP: DatosOTP? = DatosOTP(),
    @SerializedName(value = "guidConvenio", alternate = ["GuidConvenio"])
    @Expose
    var guidConvenio: String? = "",
    @SerializedName(value = "numeroTelefono", alternate = ["NumeroTelefono"])
    @Expose
    var numeroTelefono: String? = "",
    @SerializedName(value = "proveedor", alternate = ["Proveedor"])
    @Expose
    var proveedor: Int? = 0
) : Parcelable {
    constructor() : this("", DatosOTP(), "", "",0)
}