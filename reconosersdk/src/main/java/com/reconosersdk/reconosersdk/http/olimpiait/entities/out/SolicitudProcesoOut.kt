package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SolicitudProcesoOut (
    @SerializedName(value = "ProcesoConvenioGuid", alternate = ["procesoConvenioGuid"])
    @Expose
    var procesoConvenioGuid: String? = null,

    @SerializedName(value = "EstadoProceso", alternate = ["estadoProceso"])
    @Expose
    var estadoProceso : Int = 0,

    @SerializedName(value = "GuidCiu", alternate = ["guidCiu"])
    @Expose
    var guidCiu: String? = null,

    @SerializedName(value = "RespuestaTransaccion", alternate = ["respuestaTransaccion"])
    @Expose
    var respuestaTransaccion: RespuestaTransaccion? = null
) : Parcelable

