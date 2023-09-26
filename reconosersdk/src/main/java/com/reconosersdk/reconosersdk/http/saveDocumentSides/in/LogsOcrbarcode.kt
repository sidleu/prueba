package com.reconosersdk.reconosersdk.http.saveDocumentSides.`in`


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to LogOcrAnverso
 */

@Parcelize
data class LogsOcrbarcode(
    @SerializedName(value = "logOcrAnverso", alternate = ["LogOcrAnverso"])
    @Expose
    var logOcrAnverso: LogOcrAnverso? = LogOcrAnverso(),
    @SerializedName(value = "logOcrReverso", alternate = ["LogOcrReverso"])
    @Expose
    var logOcrReverso: LogOcrReverso? = LogOcrReverso(),
    @SerializedName(value = "logBarcodeReader", alternate = ["LogBarcodeReader"])
    @Expose
    var logBarcodeReader: LogBarcodeReader? = LogBarcodeReader()
) : Parcelable {
    constructor() : this(LogOcrAnverso(), LogOcrReverso(), LogBarcodeReader())
}