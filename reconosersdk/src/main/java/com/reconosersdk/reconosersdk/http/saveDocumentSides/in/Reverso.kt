package com.reconosersdk.reconosersdk.http.saveDocumentSides.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.saveDocumentSides.out.Data
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Reverso
 */

@Parcelize
data class Reverso(
     @SerializedName(value = "formato", alternate = ["Formato"])
    @Expose
    var formato: String? = "",
     @SerializedName(value = "valor", alternate = ["Valor"])
    @Expose
    var valor: String? = ""
): Parcelable {
    constructor() : this("",  "")
}