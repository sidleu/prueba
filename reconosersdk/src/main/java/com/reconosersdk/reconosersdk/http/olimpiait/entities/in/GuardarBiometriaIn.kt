package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to GuardarBiometriaIn
 */
@Parcelize
data class GuardarBiometriaIn(
    @SerializedName(value = "GuidCiu", alternate = ["guidCiu"])
    var guidCiu: String? = "",

    @SerializedName(value = "GuidProcesoConvenio", alternate = ["guidProcesoConvenio"])
    var guidProcesoConvenio: String? = "",

    @SerializedName(value = "IdServicio", alternate = ["idServicio"])
    var idServicio : Int = 0,

    @SerializedName(value = "SubTipo", alternate = ["subTipo"])
    var subTipo: String? = "",

    @SerializedName(value = "Valor", alternate = ["valor"])
    var valor: String? = "",

    @SerializedName(value = "Formato", alternate = ["formato"])
    var formato: String? = "JPG_B64",

    @SerializedName(value = "DatosAdi", alternate = ["datosAdi"])
    var datosAdi: String? = "",

    @SerializedName(value = "Usuario", alternate = ["usuario"])
    var usuario: String? = "",

    @SerializedName(value = "GuidConvenio", alternate = ["guidConvenio"])
    var guidConvenio: String? = "",

    @SerializedName(value = "Actualizar", alternate = ["actualizar"])
    var actualizar: Boolean? = false

) :  Parcelable {
    constructor() : this( "", "", 0, "","","",
        "", "","",false)
}
