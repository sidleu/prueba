package com.reconosersdk.reconosersdk.http.consultAgreementProcess.out


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import com.google.gson.annotations.Expose
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.common.DataError
import kotlinx.android.parcel.RawValue

/**
 * Object for the service to ConsultAgreementProcessOut
 */

@Parcelize
data class ConsultAgreementProcessOut(
    @SerializedName(value = "datos", alternate = ["Datos"])
    @Expose
    var datos: Datos? = Datos(),
    @SerializedName("respuestaTransaccion", alternate = ["RespuestaTransaccion"])
    @Expose
    var respuestaTransaccion: RespuestaTransaccion? = RespuestaTransaccion(),
    @SerializedName("Data")
   var dataError: @RawValue DataError? = DataError()
) : Parcelable {
    constructor() : this( Datos(),  RespuestaTransaccion(), DataError())
}
