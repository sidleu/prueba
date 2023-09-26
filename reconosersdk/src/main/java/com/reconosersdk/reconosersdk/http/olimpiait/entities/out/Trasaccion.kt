package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trasaccion(
    @SerializedName(value = "errorEntransaccion", alternate = ["ErrorEntransaccion"])
    var errorEntransaccion: List<ErrorEntransaccion>?,
    @SerializedName(value = "esExitosa", alternate = ["EsExitosa"])
    var esExitosa: Boolean?
): Parcelable {
    constructor() : this(emptyList(),  false)
}