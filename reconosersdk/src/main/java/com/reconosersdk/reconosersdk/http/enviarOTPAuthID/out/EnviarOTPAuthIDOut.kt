package com.reconosersdk.reconosersdk.http.enviarOTPAuthID.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.common.DataError
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 ** Object for the service to EnviarOTPAuthIDOut
 */

@Parcelize
data class EnviarOTPAuthIDOut(
    @SerializedName(value = "guidOTP", alternate = ["GuidOTP"])
    @Expose
    var guidOTP: String? = "",
    @SerializedName(value = "respuestaTransaccion", alternate = ["RespuestaTransaccion"])
    @Expose
    var respuestaTransaccion: RespuestaTransaccion? = RespuestaTransaccion(),
    @SerializedName("Data")
    var dataError: @RawValue DataError? = DataError()
): Parcelable {
    constructor() : this( "",  RespuestaTransaccion(), DataError())
}