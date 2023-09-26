package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to GuardarBiometria
 */
@Parcelize
data class GuardarBiometria (
    @SerializedName(value = "GuidBio", alternate = ["guidBio"])
    @Expose
    var guidBio: String? = "",

    @SerializedName(value = "RespuestaTransaccion", alternate = ["respuestaTransaccion"])
    @Expose
    var respuestaTransaccion: RespuestaTransaccion? = RespuestaTransaccion(),

    @SerializedName(value = "comparacionDocumento")
    @Expose
    var comparacionDocumento: ComparacionDocumento? = ComparacionDocumento()

):  Parcelable {
    constructor() : this( "", RespuestaTransaccion(), ComparacionDocumento())
}