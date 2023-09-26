package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class DataError(
    @SerializedName("code", alternate = ["Code"])
    var code: Int? = 0,
    @SerializedName("codeName", alternate = ["CodeName"])
    var codeName: String? = "",
    @SerializedName("Data")
    val Data: @RawValue DataX? = DataX()
): Parcelable {
    constructor() : this(0,  "", DataX())
}