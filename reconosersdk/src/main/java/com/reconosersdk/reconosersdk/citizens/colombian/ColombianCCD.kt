package com.reconosersdk.reconosersdk.citizens.colombian

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Object for the service to ColombianCCD
 */

@Parcelize
data class ColombianCCD(
    @SerializedName(value = "cedulaObverse", alternate = ["CedulaObverse"])
    @Expose
    var cedulaObverse: Int? = 0,

    @SerializedName(value = "cedulaReverse", alternate = ["CedulaReverse"])
    @Expose
    var cedulaReverse: String? = "",
    //Format yyyy-MM-dd
    @SerializedName(value = "fechaNacimientoObverse", alternate = ["FechaNacimientoObverse"])
    @Expose
    var fechaNacimientoObverse: String? = "",
    //Format yyyy-MM-dd
    @SerializedName(value = "fechaNacimientoReverse", alternate = ["FechaNacimientoReverse"])
    @Expose
    var fechaNacimientoReverse: String? = "",
    //Format yyyy-MM-dd
    @SerializedName(value = "fechaExpedicion", alternate = ["FechaExpedicion"])
    @Expose
    var fechaExpedicion: String? = "",
    //Format yyyy-MM-dd
    @SerializedName(value = "fechaExpiracionObverse", alternate = ["FechaExpiracionObverse"])
    @Expose
    var fechaExpiracionObverse: String? = "",
    //Format yyyy-MM-dd
    @SerializedName(value = "fechaExpiracionReverse", alternate = ["FechaExpiracionReverse"])
    @Expose
    var fechaExpiracionReverse: String? = "",
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

    @SerializedName(value = "namesObverse", alternate = ["NamesObverse"])
    @Expose
    var namesObverse: String? = "",

    @SerializedName(value = "lastNamesObverse", alternate = ["LastNamesObverse"])
    @Expose
    var lastNamesObverse: String? = "",

    @SerializedName(value = "namesReverse", alternate = ["NamesReverse"])
    @Expose
    var namesReverse: String? = "",

    @SerializedName(value = "lastNamesReverse", alternate = ["LastNamesReverse"])
    @Expose
    var lastNamesReverse: String? = "",

    @SerializedName(value = "rH", alternate = ["RH"])
    @Expose
    var rH: String? = ""

) : Serializable, Parcelable {

    constructor() : this(0, "","", "", "", "", "", "", "","",0, 0, "", "", "", "", "")

    companion object {
        //Simple singleton
        private var instance: ColombianCCD? = null

        fun getInstance(): ColombianCCD? {
            if (instance == null) {
                instance = ColombianCCD()
            }
            return instance
        }
    }

    override fun toString(): String {
        return "ColombianCCD { " +
                "\n" + "cedulaObverse='" + cedulaObverse + '\''.toString() +
                "\n" + "cedulaReverse='" + cedulaReverse + '\''.toString() +
                "\n" + "fechaNacimientoObverse='" + fechaNacimientoObverse + '\''.toString() +
                "\n" + "fechaNacimientoReverse='" + fechaNacimientoReverse + '\''.toString() +
                "\n" + "fechaExpedicion='" + fechaExpedicion + '\''.toString() +
                "\n" + "fechaExpiracionObverse='" + fechaExpiracionObverse + '\''.toString() +
                "\n" + "fechaExpiracionReverse='" + fechaExpiracionReverse + '\''.toString() +
                "\n" + "sexo='" + sexo + '\''.toString() +
                "\n" + "genderString='" + genderString + '\''.toString() +
                "\n" + "genderNumber='" + genderNumber + '\''.toString() +
                "\n" + "ocrState='" + ocrState + '\''.toString() +
                "\n" + "documentType='" + documentType + '\''.toString() +
                "\n" + "namesObverse='" + namesObverse + '\''.toString() +
                "\n" + "lastNamesObverse='" + lastNamesObverse + '\''.toString() +
                "\n" + "namesReverse='" + namesReverse + '\''.toString() +
                "\n" + "lastNamesReverse='" + lastNamesReverse + '\''.toString() +
                "\n" + "rH='" + rH + '\''.toString() +
                '}'.toString()
    }

}