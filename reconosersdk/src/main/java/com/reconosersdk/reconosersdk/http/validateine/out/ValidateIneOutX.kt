package com.reconosersdk.reconosersdk.http.validateine.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Object for the service to ValidateIneOutX
 */

@Parcelize
data class ValidateIneOutX(
    @SerializedName(value = "code", alternate = ["Code"])
    @Expose
    var code: Int? = 0,
    @SerializedName(value = "codeName", alternate = ["codeName"])
    @Expose
    var codeName: String? = "",
    @SerializedName("data")
    var data: Data? = Data(),
    @SerializedName("Data")
    var dataError: @RawValue DataError? = DataError()
) : Parcelable