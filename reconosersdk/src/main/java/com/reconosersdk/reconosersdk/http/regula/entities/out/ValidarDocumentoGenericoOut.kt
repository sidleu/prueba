package com.reconosersdk.reconosersdk.http.regula.entities.out

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ValidarDocumentoGenericoOut(
    val anvExitoso: Boolean?,
    val anvMensaje: String?,
    val barcodeList: List<Barcode>?,
    val ciudadanoGuid: String?,
    val `data`: Data?,
    val mrzList: List<Mrz>?,
    val ocrList: List<Ocr>?,
    val procesoConvenioGuid: String?,
    val respuestaTransaccion: RespuestaTransaccion?,
    val revExitoso: Boolean?,
    val revMensaje: String?
) : Parcelable