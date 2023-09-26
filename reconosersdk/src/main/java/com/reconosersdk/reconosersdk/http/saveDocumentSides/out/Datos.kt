package com.reconosersdk.reconosersdk.http.saveDocumentSides.out


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to Datos
 */

@Parcelize
data class Datos(
    @SerializedName("data", alternate = ["Data"])
    @Expose
    var data: Data? = Data(),
    @SerializedName("ocrList", alternate = ["OcrList"])
    @Expose
    var ocrList: List<OcrList> = emptyList(),
    @SerializedName("barcodeList", alternate = ["BarcodeList"])
    @Expose
    var barcodeList: List<BarcodeList> = emptyList(),
    @SerializedName("mrzList", alternate = ["MrzList"])
    @Expose
    var mrzList: List<MrzList> = emptyList()
): Parcelable {
    constructor() : this(Data(),  emptyList(), emptyList(), emptyList())
}