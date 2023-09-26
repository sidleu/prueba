package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savelogs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RespondLogIdentity(
    @SerializedName("respondLogMobileResult", alternate = ["RespondLogMobileResult"])
    var respondLogMobileResult: RespondLogMobileResult = RespondLogMobileResult()
): Parcelable {
    constructor() : this(RespondLogMobileResult())
}