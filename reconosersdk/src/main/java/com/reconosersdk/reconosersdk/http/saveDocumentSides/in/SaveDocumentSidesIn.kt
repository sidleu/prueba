package com.reconosersdk.reconosersdk.http.saveDocumentSides.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to SaveDocumentSidesIn
 */

@Parcelize
data class SaveDocumentSidesIn(
    @SerializedName(value = "anverso", alternate = ["Anverso"])
    @Expose
    var anverso: Anverso?=Anverso(),
    @SerializedName(value = "guidProcesoConvenio", alternate = ["GuidProcesoConvenio"])
    @Expose
     var guidProcesoConvenio: String? = "",
    @SerializedName(value = "datosAdi", alternate = ["DatosAdi"])
    @Expose
    var datosAdi: String? = "",
    @SerializedName(value = "guidCiu", alternate = ["GuidCiu"])
    @Expose
    var guidCiu: String? = "",
    @SerializedName(value = "reverso", alternate = ["Reverso"])
    @Expose
    var reverso: Reverso?= Reverso(),
    @SerializedName(value = "usuario", alternate = ["Usuario"])
    @Expose
    var usuario: String? = "",
    @SerializedName(value = "trazabilidad", alternate = ["Trazabilidad"])
    @Expose
    var trazabilidad: Trazabilidad? = Trazabilidad(),
    @SerializedName(value = "logsOcrbarcode", alternate = ["LogsOcrbarcode"])
    @Expose
    var logsOcrbarcode: LogsOcrbarcode? = LogsOcrbarcode(),
    @SerializedName(value = "configuracionEspecial", alternate = ["ConfiguracionEspecial"])
    @Expose
    var configuracionEspecial: List<String?> = emptyList()
): Parcelable {
    constructor() : this(Anverso(),   "","",  "",
        Reverso(),"", Trazabilidad(), LogsOcrbarcode(), emptyList())
}