package com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.out


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName(value = "documento", alternate = ["Documento"])
    @Expose
    var documento: String?= "",
    @SerializedName(value = "guidConvenio", alternate = ["GuidConvenio"])
    @Expose
    var guidConvenio: String?= "",
    @SerializedName(value = "nombreConvenio", alternate = ["NombreConvenio"])
    @Expose
    var nombreConvenio: String?= "",
    @SerializedName(value = "tipoDoc", alternate = ["TipoDoc"])
    @Expose
    var tipoDoc: String?= "",
    @SerializedName(value = "plataforma", alternate = ["Plataforma"])
    @Expose
    var plataforma: String?= "",
    @SerializedName(value = "appId", alternate = ["AppId"])
    @Expose
    var appId: String?= ""

) : Parcelable{
    constructor():this("","","","","","")
}