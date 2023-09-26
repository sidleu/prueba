package com.reconosersdk.reconosersdk.citizens.colombian

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForeignOCR(
    @SerializedName(value = "numero", alternate = ["Numero"])
    @Expose
    var number: Int? = 0,

    @SerializedName(value = "tipo", alternate = ["Tipo"])
    @Expose
    var tipo: String? = "",

    @SerializedName(value = "apellidos", alternate = ["Apellidos"])
    @Expose
    var apellidos: String? = "",

    @SerializedName(value = "nombres", alternate = ["Nombres"])
    @Expose
    var nombres: String? = "",


    @SerializedName(value = "fechaNacimiento", alternate = ["FechaNacimiento"])
    @Expose
    var fechaNacimiento: String? = "",

    @SerializedName(value = "fechaExpedicion", alternate = ["FechaExpedicion"])
    @Expose
    var fechaExpedicion: String? = "",

    @SerializedName(value = "fechaVencimiento", alternate = ["FechaVencimiento"])
    @Expose
    var fechaVencimiento: String? = "",

    @SerializedName(value = "sexo", alternate = ["Sexo"])
    @Expose
    var sexo: String? = "",

    @SerializedName(value = "rh", alternate = ["RH"])
    @Expose
    var rh: String? = "",

    @SerializedName(value = "nacionalidad", alternate = ["Nacionalidad"])
    @Expose
    var nacionalidad: String? = "",

    @SerializedName(value = "ocrState", alternate = ["OcrState"])
    @Expose
    var ocrState: Int? = 0


) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        number?.let { parcel.writeInt(it) }
        parcel.writeString(tipo)
        parcel.writeString(nombres)
        parcel.writeString(apellidos)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(fechaExpedicion)
        parcel.writeString(fechaVencimiento)
        parcel.writeString(sexo)
        parcel.writeString(rh)
        parcel.writeString(nacionalidad)
        ocrState?.let { parcel.writeInt(it) }
    }

    fun setForeignOCR(
        cedula: Int?,
        tipo: String?,
        nombres: String?,
        apellidos: String?,
        fechaNacimiento: String?,
        fechaExpedicion: String?,
        fechaVencimiento: String?,
        sexo: String?,
        rh: String?,
        nacionalidad: String?,
        ocrState: Int?
    ) {
        this.number = cedula
        this.tipo = tipo
        this.nombres = nombres
        this.apellidos = apellidos
        this.fechaNacimiento = fechaNacimiento
        this.fechaExpedicion = fechaExpedicion
        this.fechaVencimiento = fechaVencimiento
        this.sexo = sexo
        this.rh = rh
        this.nacionalidad = nacionalidad
        this.ocrState = ocrState
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ForeignOCR> {
        override fun createFromParcel(parcel: Parcel): ForeignOCR {
            return ForeignOCR(parcel)
        }

        override fun newArray(size: Int): Array<ForeignOCR?> {
            return arrayOfNulls(size)
        }

        //Simple singleton
        private var instance: ForeignOCR? = null

        fun getInstance(): ForeignOCR? {
            if (instance == null) {
                instance = ForeignOCR()
            }
            return instance
        }

    }

    override fun toString(): String {
        return "ForeignOCR { " +
                "\n" + "numero='" + number + '\''.toString() +
                "\n" + "tipo='" + tipo + '\''.toString() +
                "\n" + "apellidos='" + apellidos + '\''.toString() +
                "\n" + "nombres='" + nombres + '\''.toString() +
                "\n" + "fechaExpedicion='" + fechaExpedicion + '\''.toString() +
                "\n" + "fechaNacimiento='" + fechaNacimiento + '\''.toString() +
                "\n" + "fechaVencimiento='" + fechaVencimiento + '\''.toString() +
                "\n" + "sexo='" + sexo + '\''.toString() +
                "\n" + "rh='" + rh + '\''.toString() +
                "\n" + "nacionalidad='" + nacionalidad + '\''.toString() +
                "\n" + "ocrState='" + ocrState + '\''.toString() +
                '}'.toString()
    }

}