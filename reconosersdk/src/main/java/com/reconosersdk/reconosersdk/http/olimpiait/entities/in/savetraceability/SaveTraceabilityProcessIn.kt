package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.savetraceability

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaveTraceabilityProcessIn(
    @SerializedName("evento", alternate = ["Evento"])
    var evento: String? = "",
    @SerializedName("procesoConvenioGuid", alternate = ["ProcesoConvenioGuid"])
    var procesoConvenioGuid: String? = "",
    @SerializedName("trazabilidad", alternate = ["Trazabilidad"])
    var trazabilidad: Trazabilidad? = Trazabilidad()
) : Parcelable {
    constructor() : this("",  "", Trazabilidad())
}