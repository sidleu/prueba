package com.reconosersdk.reconosersdk.http.consultSteps.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to ConsultStepsIn
 */

@Parcelize
data class ConsultStepsIn(
    @SerializedName(value = "guidProcesoConvenio", alternate = ["GuidProcesoConvenio"])
    @Expose
    var guidProcesoConvenio: String? = ""
): Parcelable {
    constructor() : this( "")
}
