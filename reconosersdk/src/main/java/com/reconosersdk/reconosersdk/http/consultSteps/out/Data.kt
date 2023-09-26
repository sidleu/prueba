package com.reconosersdk.reconosersdk.http.consultSteps.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName(value = "respuestaTransaccion", alternate = ["RespuestaTransaccion"])
    @Expose
    var respuestaTransaccion: RespuestaTransaccion? = RespuestaTransaccion(),
    @SerializedName(value = "servicios", alternate = ["Servicios"])
    @Expose
    var servicios: List<Servicio?> = emptyList()
): Parcelable {
    constructor() : this(RespuestaTransaccion(), emptyList() )
}