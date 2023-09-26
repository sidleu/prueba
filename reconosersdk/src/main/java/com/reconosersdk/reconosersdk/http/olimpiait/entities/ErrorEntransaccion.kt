package com.reconosersdk.reconosersdk.http.olimpiait.entities

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorEntransaccion(
    @SerializedName(value = "Codigo", alternate = ["codigo"])
    @Expose
    var codigo: String? = "",
    @SerializedName(value = "Descripcion", alternate = ["descripcion"])
    @Expose
    var descripcion: String? = ""
) : Parcelable {
    constructor() : this("", "")
}