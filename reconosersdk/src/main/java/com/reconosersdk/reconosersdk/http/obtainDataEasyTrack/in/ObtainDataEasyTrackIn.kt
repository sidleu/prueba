package com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.`in`


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


/**
 * Object for the service to ObtainDataEasyTrackIn
 */

@Parcelize
data class ObtainDataEasyTrackIn(
    @SerializedName(value = "email", alternate = ["Email"])
    @Expose
    var email: String? = "",
) : Parcelable {
    constructor() : this("")
}