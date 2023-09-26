package com.reconosersdk.reconosersdk.http.openSource.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to ConsultarFuentesAbiertasOut
 */

@Parcelize
data class ConsultarFuentesAbiertasOut(
    @SerializedName("code", alternate = ["Code"])
    @Expose
    var code: Int? = 0,
    @SerializedName("codeName", alternate = ["CodeName"])
    @Expose
    val codeName: String? = "",
    @SerializedName("data", alternate = ["Data"])
    @Expose
    val data: DataConsulta? = DataConsulta()
): Parcelable