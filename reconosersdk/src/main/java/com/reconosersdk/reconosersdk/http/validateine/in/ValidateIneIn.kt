package com.reconosersdk.reconosersdk.http.validateine.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Barcode
 */

@Parcelize
data class ValidateIneIn(
    @SerializedName(value = "procesoConvenioGuid", alternate = ["ProcesoConvenioGuid"])
    @Expose
    var procesoConvenioGuid: String? = "",
    @SerializedName(value = "tipoValidacion", alternate = ["TipoValidacion"])
    @Expose
    var tipoValidacion: Int? = 0
): Parcelable