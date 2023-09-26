package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DatosAdi(
    @SerializedName("fechaExpDoc")
    var fechaExpDoc: String? = "",
    @SerializedName("fechaNac")
    var fechaNac: String? = "",
    @SerializedName("primerApellido")
    var primerApellido: String? = "",
    @SerializedName("primerNombre")
    var primerNombre: String? = "",
    @SerializedName("segundoApellido")
    var segundoApellido: String? = "",
    @SerializedName("segundoNombre")
    var segundoNombre: String? = ""
): Parcelable