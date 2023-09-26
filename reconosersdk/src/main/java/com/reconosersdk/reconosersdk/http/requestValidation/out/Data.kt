package com.reconosersdk.reconosersdk.http.requestValidation.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ErrorEntransaccion
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Data
 */

@Parcelize
data class Data(
    @SerializedName(value = "esExitosa", alternate = ["EsExitosa"])
    @Expose
    var esExitosa: Boolean? = false,
    @SerializedName(value = "errorEntransaccion", alternate = ["ErrorEntransaccion"])
    @Expose
    var errorEntransaccion: ErrorEntransaccion? = ErrorEntransaccion(),
    @SerializedName(value = "estadoProceso", alternate = ["EstadoProceso"])
    @Expose
    var estadoProceso: Int? = 0,
    @SerializedName(value = "guidCiudadano", alternate = ["GuidCiudadano"])
    @Expose
    var guidCiudadano: String? = "",
    @SerializedName(value = "procesoConvenioGuid", alternate = ["ProcesoConvenioGuid"])
    @Expose
    var procesoConvenioGuid: String? = "",
    @SerializedName(value = "url", alternate = ["Url"])
    @Expose
    var url: String? = ""
) : Parcelable {
    constructor() : this(false, ErrorEntransaccion(), 0, "", "", "")
}