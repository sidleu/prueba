package com.reconosersdk.reconosersdk.citizens.colombian

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Object for the service to ColombianOCR
 */


@Parcelize
data class ColombianOCR(
    @SerializedName(value = "cedula", alternate = ["Cedula"])
    @Expose
    var cedula: Int? = 0,
    //Format YYYY-MMM-DD
    @SerializedName(value = "fechaNacimiento", alternate = ["FechaNacimiento"])
    @Expose
    var fechaNacimiento: String? = "",
    //Format YYYY-MMM-DD
    @SerializedName(value = "fechaExpedicion", alternate = ["FechaExpedicion"])
    @Expose
    var fechaExpedicion: String? = "",
    //Format YYYY-MM-DD
    @SerializedName(value = "fechaElaboracion", alternate = ["FechaElaboracion"])
    @Expose
    var fechaElaboracion: String? = "",

    @SerializedName(value = "sexo", alternate = ["Sexo"])
    @Expose
    var sexo: String? = "",

    @SerializedName(value = "genderString", alternate = ["GenderString"])
    @Expose
    var genderString: String? = "",

    @SerializedName(value = "genderNumber", alternate = ["GenderNumber"])
    @Expose
    var genderNumber: String? = "",

    @SerializedName(value = "ocrState", alternate = ["OcrState"])
    @Expose
    var ocrState: Int? = 0,

    @SerializedName(value = "documentType", alternate = ["DocumentType"])
    @Expose
    var documentType: Int? = 0,

    @SerializedName(value = "names", alternate = ["Names"])
    @Expose
    var names: String? = "",

    @SerializedName(value = "lastNames", alternate = ["LastNames"])
    @Expose
    var lastNames: String? = ""

) : Serializable, Parcelable {

    constructor() : this( 0, "", "", "", "", "", "", 0,0,"", "")

    companion object  {
        //Simple singleton
        private var instance: ColombianOCR? = null

        fun getInstance(): ColombianOCR? {
            if (instance == null) {
                instance = ColombianOCR()
            }
            return instance
        }
    }

    override fun toString(): String {
        return "ColombianOCR { " +
                "\n" + "cedula='" + cedula + '\''.toString() +
                "\n" + "fechaExpedicion='" + fechaExpedicion + '\''.toString() +
                "\n" + "fechaNacimiento='" + fechaNacimiento + '\''.toString() +
                // "\n" + "fechaElaboracion='" + fechaElaboracion + '\''.toString() +
                "\n" + "sexo='" + sexo + '\''.toString() +
                "\n" + "genderString='" + genderString + '\''.toString() +
                "\n" + "genderNumber='" + genderNumber + '\''.toString() +
                "\n" + "ocrState='" + ocrState + '\''.toString() +
                "\n" + "documentType='" + documentType + '\''.toString() +
                "\n" + "names='" + names + '\''.toString() +
                "\n" + "lastNames='" + lastNames + '\''.toString() +
                '}'.toString()
    }

}