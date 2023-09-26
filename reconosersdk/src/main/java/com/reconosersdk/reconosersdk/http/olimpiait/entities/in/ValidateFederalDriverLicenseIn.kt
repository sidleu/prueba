package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ValidateFederalDriverLicenseIn(

    @SerializedName(value = "formato", alternate = ["Formato"])
    var formato: String = "",
    @SerializedName(value = "guidProcesoConvenio", alternate = ["GuidProcesoConvenio"])
    var guidProcesoConvenio: String = "",
    @SerializedName(value = "idServicio", alternate = ["IdServicio"])
    var idServicio: Int = 0,
    @SerializedName(value = "image", alternate = ["Image"])
    var image: String = "",
    @SerializedName(value = "subTipo", alternate = ["SubTipo"])
    var subTipo: Int = 0,
    @SerializedName(value = "usuario", alternate = ["Usuario"])
    var usuario: String = "",
    @SerializedName(value = "numLicencia", alternate = ["NumLicencia"])
    var numLicencia: String = "",
    @SerializedName(value = "numMedPreventiva", alternate = ["NumMedPreventiva"])
    var numMedPreventiva: String = ""
) : Parcelable {
    constructor() : this("",  "", 0,  "", 0,"", "", "")
}