package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataValidateFace(
    @SerializedName("guidConvenio", alternate = ["GuidConvenio"])
    var guidConvenio: String? = "",
    @SerializedName("codigoPais", alternate = ["CodigoPais"])
    var codigoPais: String? = "",
    @SerializedName("formato", alternate = ["Formato"])
    var formato: String? = "",
    @SerializedName("imagen", alternate = ["Imagen"])
    var imagen: String? = "",
    @SerializedName("numDocumento",  alternate = ["NumDocumento"])
    var numDocumento: String? = "",
    @SerializedName("tipoDocumento", alternate = ["TipoDocumento"])
    var tipoDocumento: String? = "",
): Parcelable