package com.reconosersdk.reconosersdk.http.regula.entities.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.ErrorEntransaccion
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RespuestaTransaccion(
    @SerializedName(value = "errorEntransaccion", alternate = ["ErrorEntransaccion"])
    @Expose
    var errorEntransaccion: List<ErrorEntransaccion>? = emptyList(),
    @SerializedName(value = "esExitosa", alternate = ["EsExitosa"])
    @Expose
    var esExitosa: Boolean? = false
) : Parcelable {
    constructor() : this(emptyList(), false)
}
