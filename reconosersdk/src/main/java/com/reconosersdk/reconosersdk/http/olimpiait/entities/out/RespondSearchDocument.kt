package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

data class RespondSearchDocument(
    val primerNombre: String? = "",
    val segundoNombre: String? = "",
    val primerApellido: String? = "",
    val segundoApellido: String? = "",
    val guidCiudadano: String? = "",
    val respuestaTransaccion: RespuestaTransaccion,
    val celular: String? = "",
    val email: String? = "",
    val tipoDoc: String? = "",
    val numDoc: String? = "",
    var enrolado: Boolean = false,
    val procesoConvenioGuidEnrolamiento: String? = ""
)