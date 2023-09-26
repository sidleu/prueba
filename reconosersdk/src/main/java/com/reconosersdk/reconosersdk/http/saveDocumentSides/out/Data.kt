package com.reconosersdk.reconosersdk.http.saveDocumentSides.out


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Object for the service to Data
 */

@Parcelize
data class Data(
    @SerializedName("documentTypeDesc", alternate = ["DocumentTypeDesc"])
    @Expose
    var documentTypeDesc: String? = "",
    @SerializedName("documentType", alternate = ["DocumentType"])
    @Expose
    var documentType: String? = "",
    @SerializedName("class", alternate = ["Class"])
    var classX: String? = "",
    @SerializedName("subTipo", alternate = ["SubTipo"])
    @Expose
    var subTipo: String? = "",
    @SerializedName("numeroDocumento", alternate = ["NumeroDocumento"])
    @Expose
    var numeroDocumento: String? = "",
    @SerializedName("primerNombre", alternate = ["PrimerNombre"])
    @Expose
    var primerNombre: String? = "",
    @SerializedName("segundoNombre", alternate = ["SegundoNombre"])
    @Expose
    var segundoNombre: String? = "",
    @SerializedName("primerApellido", alternate = ["PrimerApellido"])
    @Expose
    var primerApellido: String? = "",
    @SerializedName("segundoApellido", alternate = ["SegundoApellido"])
    @Expose
    var segundoApellido: String? = "",
    @SerializedName("nombreCompleto", alternate = ["NombreCompleto"])
    @Expose
    var nombreCompleto: String? = "",
    @SerializedName("sexo", alternate = ["Sexo"])
    @Expose
    var sexo: String? = "",
    @SerializedName("rh", alternate = ["Rh"])
    @Expose
    var rh: String? = "",
    @SerializedName("lugarNacimiento", alternate = ["LugarNacimiento"])
    @Expose
    var lugarNacimiento: String? = "",
    @SerializedName("isoCode", alternate = ["IsoCode"])
    @Expose
    var isoCode: String? = "",
    @SerializedName("mrzCode", alternate = ["MrzCode"])
    @Expose
    var mrzCode: String? = "",
    @SerializedName("pais", alternate = ["Pais"])
    @Expose
    var pais: String? = "",
    @SerializedName("edad", alternate = ["Edad"])
    @Expose
    var edad: Int? = 0,
    @SerializedName("numeroPersonal", alternate = ["NumeroPersonal"])
    @Expose
    var numeroPersonal: String? = "",
    @SerializedName("claveElector", alternate = ["ClaveElector"])
    @Expose
    var claveElector: String? = "",
    @SerializedName("ocrNumber", alternate = ["OcrNumber"])
    @Expose
    var ocrNumber: String? = "",
    @SerializedName("codigoCic", alternate = ["CodigoCic"])
    @Expose
    var codigoCic: String? = "",
    @SerializedName("numeroEmision", alternate = ["NumeroEmision"])
    @Expose
    var numeroEmision: String? = "",
    @SerializedName("fechaDeRegistro", alternate = ["FechaDeRegistro"])
    @Expose
    var fechaDeRegistro: String? = "",
    @SerializedName("fechaNacimiento", alternate = ["FechaNacimiento"])
    @Expose
    var fechaNacimiento: String? = "",
    @SerializedName("fechaExpedicionDoc", alternate = ["FechaExpedicionDoc"])
    @Expose
    var fechaExpedicionDoc: String? = "",
    @SerializedName("fechaExpiracionDoc", alternate = ["FechaExpiracionDoc"])
    @Expose
    var fechaExpiracionDoc: String? = ""
) : Parcelable {
    constructor() : this("", "", "", "", "", "" ,"",
    "", "", "", "", "", "", "", "", "", 0,
        "", "", "", "", "", "", "", "", "")

}