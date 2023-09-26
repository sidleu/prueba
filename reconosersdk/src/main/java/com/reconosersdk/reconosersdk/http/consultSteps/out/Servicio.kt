package com.reconosersdk.reconosersdk.http.consultSteps.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Servicio
 */

@Parcelize
data class Servicio(
    @SerializedName(value = "anversoOnly", alternate = ["AnversoOnly"])
    @Expose
    var anversoOnly: Boolean? = false,
    @SerializedName(value = "orden", alternate = ["Orden"])
    @Expose
    var orden: Int? = 0,
    @SerializedName(value = "servicioId", alternate = ["ServicioId"])
    @Expose
    var servicioId: Int? = 0,
    @SerializedName(value = "subTipoId", alternate = ["SubTipoId"])
    @Expose
    var subTipoId: Int? = 0
): Parcelable {
    constructor() : this(false, 0,  0, 0 )
}