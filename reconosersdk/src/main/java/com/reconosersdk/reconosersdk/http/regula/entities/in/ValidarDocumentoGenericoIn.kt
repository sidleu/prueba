package com.reconosersdk.reconosersdk.http.regula.entities.`in`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ValidarDocumentoGenericoIn(
    @SerializedName("convenioGuid")
    @Expose
    var convenioGuid: String? = null,

    @SerializedName("datosAdi")
    @Expose
    var datosAdi: String? = null,

    @SerializedName("anverso")
    @Expose
    var anverso: Anverso? = null,

    @SerializedName("reverso")
    @Expose
    var reverso: Reverso? = null,

    @SerializedName("usuario")
    @Expose
    var usuario: String? = null,

    @SerializedName("GuidProcesoConvenio")
    @Expose
    var guidProcesoConvenio: String? = null,

    @SerializedName("GuidCiu")
    @Expose
    var guidCiu: String? = null
)
