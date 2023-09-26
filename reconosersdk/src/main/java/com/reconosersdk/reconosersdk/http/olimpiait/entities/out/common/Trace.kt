package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.common

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Trace
 */

@Parcelize
data class Trace(
    @SerializedName(value = "Errors", alternate = ["errors"])
    @Expose
    var Errors: List<String?> = emptyList(),
    @SerializedName(value = "Name", alternate = ["name"])
    @Expose
    var Name: String? = ""
): Parcelable{
    constructor() : this(emptyList(),  "")
}