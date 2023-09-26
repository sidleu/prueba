package com.reconosersdk.reconosersdk.http.openSource.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to DataXOpenSource
 */

@Parcelize
data class DataXOpenSource(
    @SerializedName(value = "robot", alternate = ["Robot"])
    @Expose
    var robot: String? = "",
    @SerializedName(value = "source_name", alternate = ["Source_name"])
    @Expose
    var source_name: String? = "",
    @SerializedName(value = "status", alternate = ["Status"])
    @Expose
    var status: String? = "",
    @SerializedName(value = "texto", alternate = ["Texto"])
    @Expose
    var texto: String? = "",
    @SerializedName(value = "type", alternate = ["Type"])
    @Expose
    var type: String? = ""
) : Parcelable