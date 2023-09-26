package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EnviarOTP (
    @SerializedName(value = "GuidOTP", alternate = ["guidOTP"])
    @Expose
    var guidOTP: String? = null,

    @SerializedName(value = "RespuestaTransaccion", alternate = ["respuestaTransaccion"])
    @Expose
    var respuestaTransaccion: RespuestaTransaccion? = null
) : Parcelable
