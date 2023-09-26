package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savetraceability

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.Trasaccion
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SaveTraceabilityOut(
    @SerializedName("code", alternate = ["Code"])
    var code: Int? = 0,
    @SerializedName("codeName", alternate = ["CodeName"])
    var codeName: String? = "",
    @SerializedName("data", alternate = ["Data"])
    var data: Trasaccion? = Trasaccion()
) : Parcelable {
    constructor() : this(0, "", Trasaccion())
}