package com.reconosersdk.reconosersdk.http.validateine.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to DataX
 */

@Parcelize
data class DataX(
    @SerializedName(value = "robot", alternate = ["Robot"])
    @Expose
    var robot: String? = "",
    @SerializedName(value = "status", alternate = ["Status"])
    @Expose
    var status: String? = "",
    @SerializedName(value = "texto", alternate = ["Texto"])
    @Expose
    var texto: String? = "",
    @SerializedName(value = "type", alternate = ["Type"])
    @Expose
    var type: String? = ""
): Parcelable