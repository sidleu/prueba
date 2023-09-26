package com.reconosersdk.reconosersdk.http.validateine.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.openSource.out.Incident
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to IpScam
 */

@Parcelize
data class IpScam(
    @SerializedName(value = "incidents", alternate = ["Incidents"])
    @Expose
    var  incidents: List<Incident?> = emptyList(),
    @SerializedName(value = "isError", alternate = ["IsError"])
    @Expose
    var  isError: Boolean? = false,
    @SerializedName(value = "status", alternate = ["Status"])
    @Expose
    var  status: String? = ""
): Parcelable  {
    constructor() : this(emptyList(),  false, "")
}