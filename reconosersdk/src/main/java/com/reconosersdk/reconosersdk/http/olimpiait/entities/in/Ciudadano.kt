package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ciudadano (
    @SerializedName(value = "tipoDoc", alternate = ["TipoDoc"])
    @Expose
    var tipoDoc: String? = null,

    @SerializedName(value = "numDoc", alternate = ["NumDoc"])
    @Expose
    var numDoc: String? = null,

    @SerializedName(value = "email", alternate = ["Email"])
    @Expose
    var email: String? = null,

    @SerializedName(value = "celular", alternate = ["Celular"])
    @Expose
    var celular: String? = null,

    @SerializedName(value = "prefCelular", alternate = ["PrefCelular"])
    @Expose
    var prefCelular: String? = null
) : Parcelable
