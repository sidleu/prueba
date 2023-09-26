package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SolicitudProceso (
    @SerializedName(value = "GuidConv", alternate = ["guidConv"])
    @Expose
    var guidConv: String? = null,

    @SerializedName(value = "Asesor", alternate = ["asesor"])
    @Expose
    var asesor: String? = null,

    @SerializedName(value = "Sede", alternate = ["sede"])
    @Expose
    var sede: String? = null,

    @SerializedName(value = "CodigoCliente", alternate = ["codigoCliente"])
    @Expose
    var codigoCliente: String? = null,

    @SerializedName(value = "InfCandidato", alternate = ["infCandidato"])
    @Expose
    var infCandidato: String? = null,

    @SerializedName(value = "Finalizado", alternate = ["finalizado"])
    @Expose
    var isFinalizado : Boolean = false,

    @SerializedName(value = "Ciudadano", alternate = ["ciudadano"])
    @Expose
    var ciudadano: Ciudadano? = null,

    @SerializedName(value = "estado")
    @Expose
    var estado : Int = 0
) : Parcelable
