package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.ani

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConsultarAniIn(
    @SerializedName(value = "convenioGuid", alternate = ["ConvenioGuid"])
    @Expose
    var convenioGuid: String = "",
    @SerializedName(value = "numeroDocumento", alternate = ["NumeroDocumento"])
    @Expose
    var numeroDocumento: String ="",
    @SerializedName(value = "tipoDocumento", alternate = ["TipoDocumento"])
    @Expose
    var tipoDocumento: String = ""
) : Parcelable  {
    constructor() : this("", "", "")
}