package com.reconosersdk.reconosersdk.ui.base

import com.reconosersdk.reconosersdk.http.olimpiait.entities.out.RespuestaTransaccion

interface MvpView {

    fun showProgessDialog(msgRes: Int)
    fun dismissProgressDialog()
    fun onFinishError(code: String, message: Int)
    fun onFinishError(code: String, message: String, path: String)
    fun onFinishError(message: RespuestaTransaccion)
    fun onFinishError(code: String)
    fun onFinishError(code: String, data: String)
}