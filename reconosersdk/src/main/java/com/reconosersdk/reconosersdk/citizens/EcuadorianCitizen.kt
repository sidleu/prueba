package com.reconosersdk.reconosersdk.citizens

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reconosersdk.reconosersdk.citizens.barcode.ColombianCitizenBarcode
import java.io.Serializable

data class EcuadorianCitizen(
    @SerializedName(value = "apellidos", alternate = ["Apellidos"])
    @Expose
    var apellidos: String? = "",

    @SerializedName(value = "nombres", alternate = ["Nombres"])
    @Expose
    var nombres: String? = "",

    @SerializedName(value = "documentId", alternate = ["DocumentId"])
    @Expose
    var documentId: String? = "",

    @SerializedName(value = "fechaNacimiento", alternate = ["FechaNacimiento"])
    @Expose
    var fechaNacimiento: String? = "",

    @SerializedName(value = "fechaExpedecion", alternate = ["FechaExpedecion"])
    @Expose
    var fechaExpedecion: String? = "",

    @SerializedName(value = "fechaExpiracion", alternate = ["FechaExpiracion"])
    @Expose
    var fechaExpiracion: String? = "",

    @SerializedName(value = "sexo", alternate = ["Sexo"])
    @Expose
    var sexo: String? = "",

    @SerializedName(value = "idBarcode", alternate = ["IdBarcode"])
    @Expose
    var idBarcode: String? = "",

    @SerializedName(value = "ecuadorianDocState", alternate = ["EcuadorianDocState"])
    @Expose
    var ecuadorianDocState: Int? = 0


) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(apellidos)
        parcel.writeString(nombres)
        parcel.writeString(documentId)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(fechaExpedecion)
        parcel.writeString(fechaExpiracion)
        parcel.writeString(sexo)
        parcel.writeString(idBarcode)
        ecuadorianDocState?.let { parcel.writeInt(it) }
    }

    fun setColombianCitizen(
        apellidos: String?, nombres: String?, documentId: String?,
        fechaNacimiento: String?, fechaExpedecion: String?, fechaExpiracion: String?,
        sexo: String?, idBarcode: String?, ecuadorianDocState: Int?
    ) {
        this.apellidos = apellidos
        this.nombres = nombres
        this.documentId = documentId
        this.fechaNacimiento = fechaNacimiento
        this.fechaExpedecion = fechaExpedecion
        this.fechaExpiracion = fechaExpiracion
        this.sexo = sexo
        this.idBarcode = idBarcode
        this.ecuadorianDocState = ecuadorianDocState
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

        //Simple singleton
        private var instance: EcuadorianCitizen? = null

        fun getInstance(): EcuadorianCitizen? {
            if (instance == null) {
                instance = EcuadorianCitizen()
            }
            return instance
        }

    }

    override fun toString(): String {
        return "EcuadorianCitizen { " +
                "\n" + "Apellidos='" + apellidos + '\''.toString() +
                "\n" + "Nombres='" + nombres + '\''.toString() +
                "\n" + "# de documento='" + documentId + '\''.toString() +
                "\n" + "fechaNacimiento='" + fechaNacimiento + '\''.toString() +
                "\n" + "fechaExpedecion='" + fechaExpedecion + '\''.toString() +
                "\n" + "fechaExpiracion='" + fechaExpiracion + '\''.toString() +
                "\n" + "sexo='" + sexo + '\''.toString() +
                "\n" + "idBarcode='" + idBarcode + '\''.toString() +
                "\n" + "ecuadorianDocState='" + ecuadorianDocState + '\''.toString() +
                '}'.toString()
    }
}