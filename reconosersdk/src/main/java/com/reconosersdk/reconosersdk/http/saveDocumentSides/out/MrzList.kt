package com.reconosersdk.reconosersdk.http.saveDocumentSides.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to MrzList
 */

@Parcelize
data class MrzList(
    @SerializedName("idType", alternate = ["IdType"])
    @Expose
    var idType: String? = "",
    @SerializedName("descripcion", alternate = ["Descripcion"])
    @Expose
    var descripcion: String? = "",
    @SerializedName("valor", alternate = ["Valor"])
    @Expose
    var valor: String? = ""
): Parcelable {
    constructor() : this("",  "", "")
}