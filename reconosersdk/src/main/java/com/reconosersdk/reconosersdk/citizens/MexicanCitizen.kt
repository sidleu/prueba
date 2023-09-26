package com.reconosersdk.reconosersdk.citizens

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.citizens.barcode.ColombianCitizenBarcode
import java.io.Serializable

data class MexicanCitizen(
    @SerializedName(value = "nombres", alternate = ["Nombres"])
    @Expose
    var nombres: String? = "",

    @SerializedName(value = "domicilio", alternate = ["Domicilio"])
    @Expose
    var domicilio: String? = "",

    @SerializedName(value = "folio", alternate = ["Folio"])
    @Expose
    var folio: String? = "",

    @SerializedName(value = "anoRegistro", alternate = ["AnoRegistro"])
    @Expose
    var anoRegistro: String? = "",

    @SerializedName(value = "claveElector", alternate = ["ClaveElector"])
    @Expose
    var claveElector: String? = "",

    @SerializedName(value = "curp", alternate = ["Curp"])
    @Expose
    var curp: String? = "",

    @SerializedName(value = "estado", alternate = ["Estado"])
    @Expose
    var estado: String? = "",

    @SerializedName(value = "municipio", alternate = ["Municipio"])
    @Expose
    var municipio: String? = "",

    @SerializedName(value = "localidad", alternate = ["Localidad"])
    @Expose
    var localidad: String? = "",

    @SerializedName(value = "seccion", alternate = ["Seccion"])
    @Expose
    var seccion: String? = "",

    @SerializedName(value = "emision", alternate = ["Emision"])
    @Expose
    var emision: String? = "",

    @SerializedName(value = "vigencia", alternate = ["Vigencia"])
    @Expose
    var vigencia: String? = "",

    @SerializedName(value = "edad", alternate = ["Edad"])
    @Expose
    var edad: String? = "",

    @SerializedName(value = "sexo", alternate = ["Sexo"])
    @Expose
    var sexo: String? = ""
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombres)
        parcel.writeString(domicilio)
        parcel.writeString(folio)
        parcel.writeString(anoRegistro)
        parcel.writeString(claveElector)
        parcel.writeString(curp)
        parcel.writeString(estado)
        parcel.writeString(municipio)
        parcel.writeString(localidad)
        parcel.writeString(emision)
        parcel.writeString(vigencia)
        parcel.writeString(edad)
        parcel.writeString(sexo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ColombianCitizenBarcode> {
        override fun createFromParcel(parcel: Parcel): ColombianCitizenBarcode {
            return ColombianCitizenBarcode()
        }

        override fun newArray(size: Int): Array<ColombianCitizenBarcode?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "MexicanCitizen { " +
                "\n" + "Nombres='" + nombres + '\''.toString() +
                "\n" + "Domicilio='" + domicilio + '\''.toString() +
                "\n" + "Folio='" + folio + '\''.toString() +
                "\n" + "AñoRegistro='" + anoRegistro + '\''.toString() +
                "\n" + "ClaveElector='" + claveElector + '\''.toString() +
                "\n" + "Curp='" + curp + '\''.toString() +
                "\n" + "Estado='" + estado + '\''.toString() +
                "\n" + "Municipio='" + municipio + '\''.toString() +
                "\n" + "Localidad='" + localidad + '\''.toString() +
                "\n" + "Seccion='" + seccion + '\''.toString() +
                "\n" + "Emision='" + emision + '\''.toString() +
                "\n" + "Vigencia='" + vigencia + '\''.toString() +
                "\n" + "Edad='" + edad + '\''.toString() +
                "\n" + "Sexo='" + sexo + '\''.toString() +
                '}'.toString()
    }
}