package com.reconosersdk.reconosersdk.http.validateine.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.openSource.out.Fuentes
import com.reconosersdk.reconosersdk.http.openSource.out.Hibp
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Object for the service to Data
 */

@Parcelize
data class Data(
    @SerializedName(value = "estado", alternate = ["Estado"])
    @Expose
    var  estado: String? = "",
    @SerializedName(value = "fuentes", alternate = ["Fuentes"])
    @Expose
    var  fuentes: List<Fuentes?> = emptyList(),
    @SerializedName(value = "fuentesRaw", alternate = ["FuentesRaw"])
    @Expose
    var  fuentesRaw: List<FuentesRaw?> = emptyList(),
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
    @SerializedName(value = "transactionGuid", alternate = ["TransactionGuid"])
    @Expose
    var  transactionGuid: String? = ""
) : Parcelable