package com.reconosersdk.reconosersdk.http.consultValidation.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.openSource.out.Fuentes
import com.reconosersdk.reconosersdk.http.openSource.out.FuentesRawOpenSource
import com.reconosersdk.reconosersdk.http.openSource.out.Hibp
import com.reconosersdk.reconosersdk.http.validateine.out.IpGeo
import com.reconosersdk.reconosersdk.http.validateine.out.IpScam
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to FuentesAbiertas
 */

@Parcelize
data class FuentesAbiertas(
    @SerializedName(value = "estado", alternate = ["Estado"])
    @Expose
    var  estado: String? = "",
    @SerializedName(value = "fecha", alternate = ["Fecha"])
    @Expose
    var  fecha: String? = "",
    @SerializedName(value = "fuentes", alternate = ["Fuentes"])
    @Expose
    var  fuentes: List<Fuentes?> = emptyList(),
    @SerializedName(value = "fuentesRaw", alternate = ["FuentesRaw"])
    @Expose
    var  fuentesRaw: List<FuentesRawOpenSource?> = emptyList(),
    @SerializedName(value = "hibp", alternate = ["Hibp"])
    @Expose
    var  hibp: Hibp? = Hibp(),
    @SerializedName(value = "ipGeo", alternate = ["IpGeo"])
    @Expose
    var  ipGeo: IpGeo? = IpGeo(),
    @SerializedName(value = "ipScam", alternate = ["IpScam"])
    @Expose
    var  ipScam: IpScam? = IpScam(),
    @SerializedName(value = "queryToken", alternate = ["QueryToken"])
    @Expose
    var  queryToken: String? = "",
    @SerializedName(value = "riesgo", alternate = ["Riesgo"])
    @Expose
    var  riesgo: Boolean? = false,
    @SerializedName(value = "score", alternate = ["Score"])
    @Expose
    var  score: Int? = 0,
    @SerializedName(value = "texto", alternate = ["Texto"])
    @Expose
    var  texto: String? = "",
    @SerializedName(value = "transactionGuid", alternate = ["TransactionGuid"])
    @Expose
    var  transactionGuid: String? = ""
): Parcelable {
    constructor() : this("",  "", emptyList(), emptyList(), Hibp(),IpGeo(),IpScam(),
        "",  false,0,"", "")
}