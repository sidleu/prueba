package com.reconosersdk.reconosersdk.ui.servicesOlimpia

import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion

interface InterfaceErrorSDK {

    fun onGetError(code: String): RespuestaTransaccion
    fun onGetError(e: Throwable): RespuestaTransaccion
    fun getErrorCustom(code: String, description: String): RespuestaTransaccion
}