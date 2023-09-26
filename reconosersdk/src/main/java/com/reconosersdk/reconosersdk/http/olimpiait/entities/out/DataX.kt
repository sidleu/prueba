package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.common.Trace
import kotlinx.android.parcel.Parcelize

import kotlinx.android.parcel.RawValue

/**
 * Object for the service to DataX
 */

@Parcelize
data class DataX(
    @SerializedName("inner", alternate = ["Inner"])
    var inner: @RawValue Any? = Any(),
    @SerializedName("mesage", alternate = ["Mesage"])
    var mesage: String? = "",
    @SerializedName("trace", alternate = ["Trace"])
    var trace: List<Trace>? = emptyList(),
): Parcelable {
    constructor() : this(Any(),  "", emptyList())
}