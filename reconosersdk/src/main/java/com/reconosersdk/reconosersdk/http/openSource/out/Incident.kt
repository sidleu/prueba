package com.reconosersdk.reconosersdk.http.openSource.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Incident
 */

@Parcelize
data class Incident(
    @SerializedName("year", alternate = ["Year"])
    @Expose
    var year: String? = "",
    @SerializedName("ip", alternate = ["Ip"])
    @Expose
    var ip: String? = "",
    @SerializedName("month", alternate = ["Month"])
    @Expose
    var month: String? = "",
    @SerializedName("description", alternate = ["Description"])
    @Expose
    var description: String? = "",
    @SerializedName("domain", alternate = ["Domain"])
    @Expose
    var domain: String? = ""
) : Parcelable