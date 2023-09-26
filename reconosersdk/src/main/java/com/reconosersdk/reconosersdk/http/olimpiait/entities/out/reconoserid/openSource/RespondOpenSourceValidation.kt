package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.openSource


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.common.DataError
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RespondOpenSourceValidation(
    @SerializedName("code", alternate = ["Code"])
    var code: Int? = 0,
    @SerializedName("codeName", alternate = ["CodeName"])
    var codeName: String? = "",
    @SerializedName("data")
    var data: DataOpenSourceValidate? = DataOpenSourceValidate(),
    @SerializedName("Data")
    var dataError: @RawValue DataError? = DataError()
): Parcelable