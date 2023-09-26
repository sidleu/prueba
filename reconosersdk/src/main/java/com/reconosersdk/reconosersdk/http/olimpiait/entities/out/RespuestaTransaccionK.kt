package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

data class RespuestaTransaccionK(
    val errorEntransaccion: List<ErrorEntransaccionK>,
    val esExitosa: Boolean
)