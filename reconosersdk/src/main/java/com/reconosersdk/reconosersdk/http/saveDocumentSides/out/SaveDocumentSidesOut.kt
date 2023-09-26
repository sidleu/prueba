package com.reconosersdk.reconosersdk.http.saveDocumentSides.out


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to SaveDocumentSidesIn
 */

@Parcelize
data class SaveDocumentSidesOut(
    @SerializedName("datos", alternate = ["Datos"])
    @Expose
    var datos: Datos? = Datos(),
    @SerializedName("guidBioAnv", alternate = ["GuidBioAnv"])
    @Expose
    var guidBioAnv: String? = "",
    @SerializedName("guidBioRev", alternate = ["GuidBioRev"])
    @Expose
    var guidBioRev: String? = "",
    @SerializedName("anvExitoso", alternate = ["AnvExitoso"])
    @Expose
    var anvExitoso: Boolean = false,
    @SerializedName("revExitoso", alternate = ["RevExitoso"])
    @Expose
    var revExitoso: Boolean = false,
    @SerializedName("anvMensaje", alternate = ["AnvMensaje"])
    @Expose
    var anvMensaje: String? = "",
    @SerializedName("revMensaje", alternate = ["RevMensaje"])
    @Expose
    var revMensaje: String? = "",
    @SerializedName("respuestaTransaccion", alternate = ["RespuestaTransaccion"])
    @Expose
    var respuestaTransaccion: RespuestaTransaccion? = RespuestaTransaccion()
): Parcelable {
    constructor() : this(Datos(),  "", "",  false, false,"", "", RespuestaTransaccion())
}