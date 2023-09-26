package com.reconosersdk.reconosersdk.http.consultValidation.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.common.DataError
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Object for the service to ConsultValidationOut
 */

@Parcelize
data class ConsultValidationOut(
    @SerializedName(value = "code", alternate = ["Code"])
    @Expose
    var code: Int? = 0,
    @SerializedName(value = "codeName", alternate = ["CodeName"])
    @Expose
    var codeName: String? = "",
    @SerializedName(value = "data")
    var data: Data = Data(),
    @SerializedName("Data")
    var dataError: @RawValue DataError? = DataError()
): Parcelable {
    constructor() : this(0,  "", Data(),  DataError())
}