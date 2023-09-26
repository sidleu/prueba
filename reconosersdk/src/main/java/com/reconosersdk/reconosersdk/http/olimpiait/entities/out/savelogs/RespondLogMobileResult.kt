package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.savelogs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.ErrorEntransaccion
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RespondLogMobileResult(
    @SerializedName("errorEntransaccion", alternate = ["ErrorEntransaccion"])
    var errorEntransaccion: List<ErrorEntransaccion> = listOf(),
    @SerializedName("esExitosa", alternate = ["EsExitosa"])
    var esExitosa: Boolean = false
): Parcelable {
    constructor() : this(listOf(),  false)
}