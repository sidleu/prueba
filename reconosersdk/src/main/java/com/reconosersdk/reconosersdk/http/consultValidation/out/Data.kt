package com.reconosersdk.reconosersdk.http.consultValidation.out

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
    @SerializedName(value = "aprobado", alternate = ["Aprobado"])
    @Expose
    var aprobado: Boolean? = false,
    @SerializedName(value = "asesor", alternate = ["Asesor"])
    @Expose
    var asesor: String? = "",
    @SerializedName(value = "cancelado", alternate = ["Cancelado"])
    @Expose
    var cancelado: Boolean? = false,
    @SerializedName(value = "celular", alternate = ["Celular"])
    @Expose
    var celular: String? = "",
    @SerializedName(value = "codigoCliente", alternate = ["CodigoCliente"])
    @Expose
    var codigoCliente: String? = "",
    @SerializedName(value = "comparacionRostroDocumento", alternate = ["ComparacionRostroDocumento"])
    @Expose
    var comparacionRostroDocumento: Boolean? = false,
    @SerializedName(value = "email", alternate = ["Email"])
    @Expose
    var email: String? = "",
    @SerializedName(value = "encontradoEnFuente", alternate = ["EncontradoEnFuente"])
    @Expose
    var encontradoEnFuente: Boolean? = false,
    @SerializedName(value = "estadoDescripcion", alternate = ["EstadoDescripcion"])
    @Expose
    var estadoDescripcion: String? = "",
    @SerializedName(value = "estadoProceso", alternate = ["EstadoProceso"])
    @Expose
    var estadoProceso: Int? = 0,
    @SerializedName(value = "fechaFinalizacion", alternate = ["FechaFinalizacion"])
    @Expose
    var fechaFinalizacion: String? = "",
    @SerializedName(value = "fechaRegistro", alternate = ["FechaRegistro"])
    @Expose
    var fechaRegistro: String? = "",
    @SerializedName(value = "finalizado", alternate = ["Finalizado"])
    @Expose
    var finalizado: Boolean? = false,
    @SerializedName(value = "fuentesAbiertas", alternate = ["FuentesAbiertas"])
    @Expose
    var fuentesAbiertas: FuentesAbiertas? = FuentesAbiertas(),
    @SerializedName(value = "guidConv", alternate = ["GuidConv"])
    @Expose
    var guidConv: String? = "",
    @SerializedName(value = "motivoCancelacion", alternate = ["MotivoCancelacion"])
    @Expose
    var motivoCancelacion: @RawValue Any? = Any(),
    @SerializedName(value = "motivoId", alternate = ["MotivoId"])
    @Expose
    var motivoId: Int? = 0,
    @SerializedName(value = "nombreSede", alternate = ["NombreSede"])
    @Expose
    var nombreSede: String? = "",
    @SerializedName(value = "numDoc", alternate = ["NumDoc"])
    @Expose
    var numDoc: String? = "",
    @SerializedName(value = "primerApellido", alternate = ["PrimerApellido"])
    @Expose
    var primerApellido: String? = "",
    @SerializedName(value = "primerNombre", alternate = ["PrimerNombre"])
    @Expose
    var primerNombre: String? = "",
    @SerializedName(value = "procesoConvenioGuid", alternate = ["ProcesoConvenioGuid"])
    @Expose
    var procesoConvenioGuid: String? = "",
    @SerializedName(value = "scoreProceso", alternate = ["ScoreProceso"])
    @Expose
    var scoreProceso: @RawValue Any? = Any(),
    @SerializedName(value = "scoreRostroDocumento", alternate = ["ScoreRostroDocumento"])
    @Expose
    var scoreRostroDocumento: Int? = 0,
    @SerializedName(value = "sede", alternate = ["Sede"])
    @Expose
    var sede: String? = "",
    @SerializedName(value = "segundoApellido", alternate = ["SegundoApellido"])
    @Expose
    var segundoApellido: String? = "",
    @SerializedName(value = "segundoNombre", alternate = ["SegundoNombre"])
    @Expose
    var segundoNombre: String? = "",
    @SerializedName(value = "servicios", alternate = ["Servicios"])
    @Expose
    var servicios: List<Servicio?> = emptyList(),
    @SerializedName(value = "tipoDoc", alternate = ["TipoDoc"])
    @Expose
    var tipoDoc: String? = ""
): Parcelable {
    constructor() : this(false,  "", false,  "", "", false, "",
    false, "", 0, "", "", false, FuentesAbiertas(),
    "", Any(), 0, "", "", "", "", "", Any(), 0,
    "", "", "", emptyList(), "")
}