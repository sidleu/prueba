package com.reconosersdk.reconosersdk.http.validateine.out

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.common.Trace
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Object for the service to DataError
 */

@Parcelize
data class DataError(
    @SerializedName("Inner", alternate = ["inner"])
    var inner: @RawValue Any? = Any(),
    @SerializedName("Message", alternate = ["message"])
    var message: String? = "",
    @SerializedName("Trace", alternate = ["trace"])
    var trace: List<Trace?> = emptyList()
): Parcelable {
    constructor() : this(Any(),  "", emptyList())
}