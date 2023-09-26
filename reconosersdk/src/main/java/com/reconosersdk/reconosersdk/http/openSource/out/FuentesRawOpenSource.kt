package com.reconosersdk.reconosersdk.http.openSource.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to FuentesRawOpenSource
 */

@Parcelize
data class FuentesRawOpenSource(
    @SerializedName(value = "nombre", alternate = ["Nombre"])
    @Expose
    var nombre: String? = "",
    @SerializedName(value = "codigo", alternate = ["Codigo"])
    @Expose
    var codigo: String? = "",
    @SerializedName(value = "estado", alternate = ["Estado"])
    @Expose
    var estado: String? = "",
    @SerializedName(value = "texto", alternate = ["Texto"])
    @Expose
    var texto: String? = "",
    @SerializedName(value = "tipo", alternate = ["Tipo"])
    @Expose
    var tipo: String? = "",
    @SerializedName(value = "error", alternate = ["Error"])
    @Expose
    var error: Boolean? = false,
    /*@SerializedName(value = "data", alternate = ["Data"])
    var dataXOpenSource: DataXOpenSource? = DataXOpenSource()*/
) : Parcelable {
    constructor() : this("",  "", "",  "","",false)
}