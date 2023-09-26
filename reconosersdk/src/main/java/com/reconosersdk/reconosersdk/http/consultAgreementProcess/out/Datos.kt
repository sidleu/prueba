package com.reconosersdk.reconosersdk.http.consultAgreementProcess.out


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.RawValue

/**
 * Object for the service to Datos
 */

@Parcelize
data class Datos(
    @SerializedName("guidConv", alternate = ["GuidConv"])
    @Expose
    var guidConv: String? = "",
    @SerializedName("procesoConvenioGuid", alternate = ["ProcesoConvenioGuid"])
    @Expose
    var procesoConvenioGuid: String? = "",
    @SerializedName("guidCiu", alternate = ["GuidCiu"])
    @Expose
    var guidCiu: String? = "",
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
    @SerializedName("infCandidato", alternate = ["InfCandidato"])
    @Expose
    var infCandidato: String? = "",
    @SerializedName("tipoDoc", alternate = ["TipoDoc"])
    @Expose
    var tipoDoc: String? = "",
    @SerializedName("numDoc", alternate = ["NumDoc"])
    @Expose
    var numDoc: String? = "",
    @SerializedName("email", alternate = ["Email"])
    @Expose
    var email: String? = "",
    @SerializedName("celular", alternate = ["Celular"])
    @Expose
    var celular: String? = "",
    @SerializedName("asesor", alternate = ["Asesor"])
    @Expose
    var asesor: String? = "",
    @SerializedName("sede", alternate = ["Sede"])
    @Expose
    var sede: String? = "",
    @SerializedName("nombreSede", alternate = ["NombreSede"])
    @Expose
    var nombreSede: String? = "",
    @SerializedName("codigoCliente", alternate = ["CodigoCliente"])
    @Expose
    var codigoCliente: String? = "",
    @SerializedName("ejecutarEnMovil", alternate = ["EjecutarEnMovil"])
    @Expose
    var ejecutarEnMovil: Boolean? = false,
    @SerializedName("estadoProceso", alternate = ["EstadoProceso"])
    @Expose
    var estadoProceso: Int? = 0,
    @SerializedName("finalizado", alternate = ["Finalizado"])
    @Expose
    var finalizado: Boolean? = false,
    @SerializedName("estadoForense", alternate = ["EstadoForense"])
    @Expose
    var estadoForense: Int? = 0,
    @SerializedName("fechaRegistro", alternate = ["FechaRegistro"])
    @Expose
    var fechaRegistro: String? = "",
    @SerializedName("fechaFinalizacion", alternate = ["FechaFinalizacion"])
    @Expose
    var fechaFinalizacion: String? = "",
    @SerializedName("fechaExpedicion", alternate = ["FechaExpedicion"])
    @Expose
    var fechaExpedicion: String? = "",
    @SerializedName("lugarExpedicion", alternate = ["LugarExpedicion"])
    @Expose
    var lugarExpedicion: String? = "",
    @SerializedName("biometrias", alternate = ["Biometrias"])
    @Expose
    var biometrias: List<Biometrias>? = emptyList()
) : Parcelable {
    constructor() : this(
        "", "", "", "", "", "", "",
        "", "", "", "", "", "",
        "", "", "", false, 0,
        false, 0,"", "","", "",emptyList() )
}
