package com.reconosersdk.reconosersdk.http.openSource.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to FuentesAbiertas
 */

@Parcelize
data class Hibp(
    @SerializedName("breaches", alternate = ["Breaches"])
    @Expose
    var fuentes: List<Breaches?> = emptyList(),
    @SerializedName("isError", alternate = ["IsError"])
    @Expose
    var isError: Boolean? = false,
    @SerializedName("status", alternate = ["Status"])
    @Expose
    var status: String? = "",
    @SerializedName("raw", alternate = ["Raw"])
    @Expose
    var raw: String? = ""
) : Parcelable {
    constructor() : this(emptyList(),  false, "",  "")
}