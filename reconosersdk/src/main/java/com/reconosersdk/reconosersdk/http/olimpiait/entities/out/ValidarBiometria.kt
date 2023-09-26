package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ValidarBiometria (
    @SerializedName(value = "EsValido", alternate = ["esValido"])
    @Expose
    var isEsValido : Boolean? = false,

    @SerializedName(value = "Score", alternate = ["score"])
    @Expose
    var score: String? = "",

    @SerializedName(value = "Intentos", alternate = ["intentos"])
    @Expose
    var intentos : Int? = 0,

    @SerializedName(value = "RespuestaTransaccion", alternate = ["respuestaTransaccion"])
    @Expose
    var respuestaTransaccion: RespuestaTransaccion? = RespuestaTransaccion()
) : Parcelable {
    constructor() : this(false, "", 0, RespuestaTransaccion())
}