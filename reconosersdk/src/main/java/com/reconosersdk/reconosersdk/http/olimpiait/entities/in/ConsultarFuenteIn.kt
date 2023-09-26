package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConsultarFuenteIn(
    @SerializedName("codigoPais", alternate = ["CodigoPais"])
    var codigoPais: String = "",
    @SerializedName("guidConvenio", alternate = ["GuidConvenio"])
    var convenioGuid: String = "",
    @SerializedName("numDocumento", alternate = ["NumDocumento"])
    var numDocumento: String = "",
    @SerializedName("tipoDocumento", alternate = ["TipoDocumento"])
    var tipoDocumento: String = ""
): Parcelable