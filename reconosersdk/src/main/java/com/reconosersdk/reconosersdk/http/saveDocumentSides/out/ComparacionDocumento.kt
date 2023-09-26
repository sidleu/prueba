package com.reconosersdk.reconosersdk.http.saveDocumentSides.out


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to ComparacionDocumento
 */

@Parcelize
data class ComparacionDocumento(
    @SerializedName("bioGuidAnv", alternate = ["BioGuidAnv"])
    @Expose
    var bioGuidAnv: String? = "",
    @SerializedName("comparacionSelfie", alternate = ["ComparacionSelfie"])
    @Expose
    var comparacionSelfie: Boolean? = false,
    @SerializedName("mensaje", alternate = ["Mensaje"])
    @Expose
    var mensaje: String? = "",
    @SerializedName("mensajeReverso", alternate = ["MensajeReverso"])
    @Expose
    var mensajeReverso: String? = "",
    @SerializedName("porcentajeValidacionANI", alternate = ["PorcentajeValidacionANI"])
    @Expose
    var porcentajeValidacionANI: Int? = 0,
    @SerializedName("porcentajeValidacionBarcode", alternate = ["PorcentajeValidacionBarcode"])
    @Expose
    var porcentajeValidacionBarcode: Int? = 0,
    @SerializedName("scoreSelfie", alternate = ["ScoreSelfie"])
    @Expose
    var scoreSelfie: Int? = 0,
    @SerializedName("validacionAnverso", alternate = ["ValidacionAnverso"])
    @Expose
    var validacionAnverso: Boolean? = false,
    @SerializedName("validacionReverso", alternate = ["ValidacionReverso"])
    @Expose
    var validacionReverso: Boolean? = false
) : Parcelable