package com.reconosersdk.reconosersdk.http.saveDocumentSides.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Anverso
 */

@Parcelize
data class Anverso(

    @SerializedName(value = "formato", alternate = ["Formato"])
    @Expose
    var formato: String? = "",
    @SerializedName(value = "valor", alternate = ["Valor"])
    @Expose
    var valor: String? = ""
): Parcelable{
    constructor() : this("",  "")
}