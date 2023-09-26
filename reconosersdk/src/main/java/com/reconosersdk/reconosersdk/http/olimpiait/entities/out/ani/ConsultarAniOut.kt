package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.ani

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion

data class ConsultarAniOut(
    @SerializedName(value = "departamentoExpedicion", alternate = ["DepartamentoExpedicion"])
    @Expose
    var departamentoExpedicion: Any? = null,
    @SerializedName(value = "estadoDoc", alternate = ["EstadoDoc"])
    @Expose
    var estadoDoc: String? = "",
    @SerializedName(value = "fechaExp", alternate = ["FechaExp"])
    @Expose
    var fechaExp: String? = "",
    @SerializedName(value = "localizacion", alternate = ["Localizacion"])
    @Expose
    var localizacion: Any? = null,
    @SerializedName(value = "nuip", alternate = ["Nuip"])
    @Expose
    var nuip: Any? = null,
    @SerializedName(value = "numeroResolucion", alternate = ["NumeroResolucion"])
    @Expose
    var numeroResolucion: Any? = null,
    @SerializedName(value = "particula", alternate = ["Particula"])
    @Expose
    var particula: Any? = null,
    @SerializedName(value = "primerApellido", alternate = ["PrimerApellido"])
    @Expose
    var primerApellido: String? = "",
    @SerializedName(value = "primerNombre", alternate = ["PrimerNombre"])
    @Expose
    var primerNombre: String? = "",
    @SerializedName(value = "RespuestaTransaccion", alternate = ["respuestaTransaccion"])
    @Expose
    val respuestaTransaccion: RespuestaTransaccion? = RespuestaTransaccion(),
    @SerializedName(value = "segundoApellido", alternate = ["SegundoApellido"])
    @Expose
    var segundoApellido: String? = "",
    @SerializedName(value = "segundoNombre", alternate = ["SegundoNombre"])
    @Expose
    var segundoNombre: String? = "",
    @SerializedName(value = "vivo", alternate = ["Vivo"])
    @Expose
    var vivo: Boolean? = false
) {
    constructor() : this(null, "", "", null, null, null, null,
        "", "", RespuestaTransaccion(), "", "", false)
}