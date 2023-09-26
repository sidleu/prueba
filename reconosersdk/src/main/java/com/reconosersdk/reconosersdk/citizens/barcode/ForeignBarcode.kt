package com.reconosersdk.reconosersdk.citizens.barcode

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForeignBarcode(
    @SerializedName(value = "numero", alternate = ["Numero"])
    @Expose
    var number: Int? = 0,

    @SerializedName(value = "tipo", alternate = ["Tipo"])
    @Expose
    var tipo: String? = "",

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

    @SerializedName(value = "typeDocument", alternate = ["TypeDocument"])
    @Expose
    var typeDocument: String? = ""


) :Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
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
        number?.let { parcel.writeInt(it) }
        parcel.writeString(tipo)
        parcel.writeString(nombres)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(fechaExpedicion)
        parcel.writeString(fechaVencimiento)
        parcel.writeString(sexo)
        parcel.writeString(rh)
        parcel.writeString(nacionalidad)
        parcel.writeString(typeDocument)
    }

    fun setForeignBarcode(
        cedula: Int?,
        tipo: String?,
        nombres: String?,
        fechaNacimiento: String?,
        fechaExpedicion: String?,
        fechaVencimiento: String?,
        sexo: String?,
        rh: String?,
        nacionalidad: String?,
        typeDocument: String?
    ) {
        this.number = cedula
        this.tipo = tipo
        this.nombres = nombres
        this.fechaNacimiento = fechaNacimiento
        this.fechaExpedicion = fechaExpedicion
        this.fechaVencimiento = fechaVencimiento
        this.sexo = sexo
        this.rh = rh
        this.nacionalidad = nacionalidad
        this.typeDocument = typeDocument
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ForeignBarcode> {
        override fun createFromParcel(parcel: Parcel): ForeignBarcode {
            return ForeignBarcode(parcel)
        }

        override fun newArray(size: Int): Array<ForeignBarcode?> {
            return arrayOfNulls(size)
        }

        //Simple singleton
        private var instance: ForeignBarcode? = null

        fun getInstance(): ForeignBarcode? {
            if (instance == null) {
                instance = ForeignBarcode()
            }
            return instance
        }

    }

    override fun toString(): String {
        return "ForeignBarcode { " +
                "\n" + "numero='" + number + '\''.toString() +
                "\n" + "tipo='" + tipo + '\''.toString() +
                "\n" + "nombres='" + nombres + '\''.toString() +
                "\n" + "fechaExpedicion='" + fechaExpedicion + '\''.toString() +
                "\n" + "fechaNacimiento='" + fechaNacimiento + '\''.toString() +
                "\n" + "fechaVencimiento='" + fechaVencimiento + '\''.toString() +
                "\n" + "sexo='" + sexo + '\''.toString() +
                "\n" + "rh='" + rh + '\''.toString() +
                "\n" + "nacionalidad='" + nacionalidad + '\''.toString() +
                "\n" + "typeDocument='" + typeDocument + '\''.toString() +
                '}'.toString()
    }
}