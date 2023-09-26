package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Object for the service to DataError
 */

@Parcelize
data class DataError(
    @SerializedName("Inner", alternate = ["inner"])
    var inner: @RawValue Any? = Any(),
    @SerializedName("Mesage", alternate = ["mesage"])
    var mesage: String? = "",
    @SerializedName("Trace", alternate = ["trace"])
    var trace: List<Trace?> = emptyList()
): Parcelable {
    constructor() : this(Any(),  "", emptyList())
}