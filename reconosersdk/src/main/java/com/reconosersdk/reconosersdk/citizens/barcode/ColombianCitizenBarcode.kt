package com.reconosersdk.reconosersdk.citizens.barcode

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.utils.Constants
import kotlinx.android.parcel.Parcelize

/**
 * Object for the service to ColombianCitizenBarcode
 */

@Parcelize
data class ColombianCitizenBarcode(

    @SerializedName(value = "primerApellido", alternate = ["PrimerApellido"])
    @Expose
    var primerApellido: String? = "",

    @SerializedName(value = "segundoApellido", alternate = ["SegundoApellido"])
    @Expose
    var segundoApellido: String? = "",

    @SerializedName(value = "primerNombre", alternate = ["PrimerNombre"])
    @Expose
    var primerNombre: String? = "",

    @SerializedName(value = "segundoNombre", alternate = ["SegundoNombre"])
    @Expose
    var segundoNombre: String? = "",

    @SerializedName(value = "cedula", alternate = ["Cedula"])
    @Expose
    var cedula: String? = "",

    @SerializedName(value = "rh", alternate = ["Rh"])
    @Expose
    var rh: String? = "",
    //Format YYYY-MM-DD
    @SerializedName(value = "fechaNacimiento", alternate = ["FechaNacimiento"])
    @Expose
    var fechaNacimiento: String? = Constants.COLOMBIAN_CC_NOT_DATE,

    @SerializedName(value = "sexo", alternate = ["Sexo"])
    @Expose
    var sexo: String? = "",

    @SerializedName(value = "typeDocument", alternate = ["TypeDocument"])
    @Expose
    var typeDocument: String? = "",


) : Parcelable {
    constructor() : this( "", "", "", "", "", "", "", "", "")

    companion object  {
        //Simple singleton
        private var instance: ColombianCitizenBarcode? = null

        fun getInstance(): ColombianCitizenBarcode? {
            if (instance == null) {
                instance = ColombianCitizenBarcode()
            }
            return instance
        }
    }

    override fun toString(): String {
        return "ColombianCitizenBarcode { " +
                "\n" + "primerApellido='" + primerApellido + '\''.toString() +
                "\n" + "segundoApellido='" + segundoApellido + '\''.toString() +
                "\n" + "primerNombre='" + primerNombre + '\''.toString() +
                "\n" + "segundoNombre='" + segundoNombre + '\''.toString() +
                "\n" + "cedula='" + cedula + '\''.toString() +
                "\n" + "rh='" + rh + '\''.toString() +
                "\n" + "fechaNacimiento='" + fechaNacimiento + '\''.toString() +
                "\n" + "sexo='" + sexo + '\''.toString() +
                "\n" + "typeDocument='" + typeDocument + '\''.toString() +
                '}'.toString()
    }
}