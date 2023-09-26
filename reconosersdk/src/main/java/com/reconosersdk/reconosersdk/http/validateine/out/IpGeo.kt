package com.reconosersdk.reconosersdk.http.validateine.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to IpGeo
 */

@Parcelize
data class IpGeo(
    @SerializedName(value = "city", alternate = ["City"])
    @Expose
    var city: String? = "",
    @SerializedName(value = "country", alternate = ["Country"])
    @Expose
    var country: String? = "",
    @SerializedName(value = "ip", alternate = ["Ip"])
    @Expose
    var ip: String? = "",
    @SerializedName(value = "isError", alternate = ["IsError"])
    @Expose
    var isError: Boolean? = false,
    @SerializedName(value = "isoCode", alternate = ["IsoCode"])
    @Expose
    var isoCode: String? = "",
    @SerializedName(value = "postalCode", alternate = ["PostalCode"])
    @Expose
    var postalCode: String? = "",
    @SerializedName(value = "status", alternate = ["Status"])
    @Expose
    var status: String? = "",
    @SerializedName(value = "subdivision", alternate = ["Subdivision"])
    @Expose
    var subdivision: String? = ""
): Parcelable {
    constructor() : this("",  "", "",  false, "", "", "", "")
}