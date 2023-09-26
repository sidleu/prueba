package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

data class RespondSearchBiometry(
    var datosAdi: String,
    var fecha: String,
    var formato: String,
    var guidBio: String,
    var valor: String,
    var respuestaTransaccion: RespuestaTransaccionK
)