package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CiudadanoIn(
    @SerializedName(value = "guidCiu", alternate = ["GuidCiu"])
    @Expose
    var guidCiu: String? = null,

    @SerializedName(value = "guidConv", alternate = ["GuidConv"])
    @Expose
    var guidConv: String? = null,

    @SerializedName(value = "tipoDoc", alternate = ["TipoDoc"])
    @Expose
    var tipoDoc: String? = null,

    @SerializedName(value = "numDoc", alternate = ["NumDoc"])
    @Expose
    var numDoc: String? = null,

    @SerializedName(value = "email", alternate = ["Email"])
    @Expose
    var email: String? = null,

    @SerializedName(value = "codPais", alternate = ["CodPais"])
    @Expose
    var codPais: String? = null,

    @SerializedName(value = "celular", alternate = ["Celular"])
    @Expose
    var celular: String? = null,

    @SerializedName(value = "datosAdi", alternate = ["DatosAdi"])
    @Expose
    var datosAdi: DatosAdi? = null,

    @SerializedName(value = "procesoConvenioGuid", alternate = ["ProcesoConvenioGuid"])
    @Expose
    var procesoConvenioGuid: String? = null,

    @SerializedName(value = "asesor", alternate = ["Asesor"])
    @Expose
    var asesor: String? = null,

    @SerializedName(value = "sede", alternate = ["Sede"])
    @Expose
    var sede: String? = null,
): Parcelable