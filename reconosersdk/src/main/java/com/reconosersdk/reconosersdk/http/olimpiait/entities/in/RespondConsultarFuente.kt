package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.common.DataError
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid.DataRespondConsultarFuente
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RespondConsultarFuente(
    @SerializedName("code", alternate = ["Code"])
    var code: Int? = 0,
    @SerializedName("codeName", alternate = ["CodeName"])
    var codeName: String? = "",
    @SerializedName("data")
    var data: DataRespondConsultarFuente? = DataRespondConsultarFuente(),
    @SerializedName("Data")
    var dataError: @RawValue DataError? = DataError()
): Parcelable