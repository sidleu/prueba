package com.reconosersdk.reconosersdk.http.validateine.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to FuentesRaw
 */

@Parcelize
data class FuentesRaw(
    @SerializedName(value = "codigo", alternate = ["Codigo"])
    @Expose
    var  codigo: String? = "",
    @SerializedName(value = "data", alternate = ["Data"])
    var  data: DataX? = DataX(),
    @SerializedName(value = "error", alternate = ["Error"])
    @Expose
    var  error: Boolean? = false,
    @SerializedName(value = "estado", alternate = ["Estado"])
    @Expose
    var  estado: String?= ""
): Parcelable