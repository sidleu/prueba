package com.reconosersdk.reconosersdk.http.openSource.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.validateine.out.IpGeo
import com.reconosersdk.reconosersdk.http.validateine.out.IpScam
import kotlinx.android.parcel.Parcelize


/**
 * Object for the service to ConsultarFuentesAbiertasOut
 */

@Parcelize
data class DataConsulta(
    @SerializedName("transaccionGuid", alternate = ["TransaccionGuid"])
    @Expose
    var  transaccionGuid: String? = "",
    @SerializedName("estado", alternate = ["Estado"])
    @Expose
    var  estado: String? = "",
    @SerializedName("queryToken", alternate = ["QueryToken"])
    @Expose
    var  queryToken: String? = "",
    @SerializedName("riesgo", alternate = ["Riesgo"])
    @Expose
    var  riesgo: Boolean? = false,
    @SerializedName("score", alternate = ["Score"])
    @Expose
    var  score: Int? = 0,
    @SerializedName("texto", alternate = ["Texto"])
    @Expose
    var  texto: String? = "",
    @SerializedName("ipScam", alternate = ["IpScam"])
    @Expose
    var  ipScam: IpScam? = IpScam(),
    @SerializedName("ipGeo", alternate = ["IpGeo"])
    @Expose
    var  ipGeo: IpGeo? = IpGeo(),
    @SerializedName("fuentes", alternate = ["Fuentes"])
    @Expose
    var  fuentes: List<Fuentes?> = emptyList(),
    @SerializedName("fuentesRaw", alternate = ["FuentesRaw"])
    @Expose
    var  fuentesRaw: List<FuentesRawOpenSource?> = emptyList(),
    @SerializedName("hibp", alternate = ["Hibp"])
    @Expose
    var  hibp: Hibp? = Hibp(),
    @SerializedName("fecha", alternate = ["Fecha"])
    @Expose
    var  fecha: String? = ""
) : Parcelable
