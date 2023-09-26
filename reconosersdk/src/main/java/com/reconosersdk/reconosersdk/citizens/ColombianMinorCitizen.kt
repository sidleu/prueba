package com.reconosersdk.reconosersdk.citizens

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.Nullable

import java.io.Serializable

data class ColombianMinorCitizen(

    @SerializedName(value = "apellidos", alternate = ["Apellidos"])
    @Expose
    var apellidos: String? = "",

    @SerializedName(value = "nombres", alternate = ["Nombres"])
    @Expose
    var nombres: String? = "",

    @SerializedName(value = "numero", alternate = ["Numero"])
    @Expose
    var numero: String? = "",

    @SerializedName(value = "fechaNacimiento", alternate = ["FechaNacimiento"])
    @Expose
    var fechaNacimiento: String? = "",

    @SerializedName(value = "lugarNacimiento", alternate = ["LugarNacimiento"])
    @Expose
    var lugarNacimiento: String? = "",

    @SerializedName(value = "fechaVencimiento", alternate = ["FechaVencimiento"])
    @Expose
    var fechaVencimiento: String? = "",

    @SerializedName(value = "rh", alternate = ["Rh"])
    @Expose
    var rh: String? = "",

    @SerializedName(value = "fechaExpedicion", alternate = ["FechaExpedicion"])
    @Expose
    var fechaExpedicion: String? = "",

    @SerializedName(value = "sexo", alternate = ["Sexo"])
    @Expose
    var sexo: String? = ""

): Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
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
        parcel.writeString(apellidos)
        parcel.writeString(nombres)
        parcel.writeString(numero)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(lugarNacimiento)
        parcel.writeString(fechaVencimiento)
        parcel.writeString(fechaExpedicion)
        parcel.writeString(rh)
        parcel.writeString(sexo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ColombianMinorCitizen> {
        override fun createFromParcel(parcel: Parcel): ColombianMinorCitizen {
            return ColombianMinorCitizen(parcel)
        }

        override fun newArray(size: Int): Array<ColombianMinorCitizen?> {
            return arrayOfNulls(size)
        }
    }

    fun setColombianMinorCitizen(
        apellidos: @Nullable String?,
        nombres: @Nullable String?,
        numero: @Nullable String?,
        fechaNacimiento: @Nullable String?,
        lugarNacimiento: @Nullable String?,
        fechaVencimiento: @Nullable String?,
        rh: @Nullable String?,
        fechaExpedicion: @Nullable String?,
        sexo: @Nullable String?
    ) {
        this.apellidos = apellidos
        this.nombres = nombres
        this.numero = numero
        this.fechaNacimiento = fechaNacimiento
        this.lugarNacimiento = lugarNacimiento
        this.fechaVencimiento = fechaVencimiento
        this.rh = rh
        this.fechaExpedicion = fechaExpedicion
        this.sexo = sexo
    }

    override fun toString(): String {
        return "colombianMinorCitizen { " +
                "\n" + "apellidos='" + apellidos + '\''.toString() +
                "\n" + "nombres='" + nombres + '\''.toString() +
                "\n" + "numero='" + numero + '\''.toString() +
                "\n" + "fechaNacimiento='" + fechaNacimiento + '\''.toString() +
                "\n" + "lugarNacimiento='" + lugarNacimiento + '\''.toString() +
                "\n" + "fechaVencimiento='" + fechaVencimiento + '\''.toString() +
                "\n" + "fechaExpedicion='" + fechaExpedicion + '\''.toString() +
                "\n" + "rh='" + rh + '\''.toString() +
                "\n" + "sexo='" + sexo + '\''.toString() +
                '}'.toString()
    }
}