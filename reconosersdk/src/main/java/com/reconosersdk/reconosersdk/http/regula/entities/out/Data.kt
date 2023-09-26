package com.reconosersdk.reconosersdk.http.regula.entities.out

import android.os.Parcel
import android.os.Parcelable

data class Data(
    val `class`: String?,
    val claveElector: String?,
    val codigoCic: String?,
    val documentType: String?,
    val documentTypeDesc: String?,
    val edad: Int?,
    val fechaDeRegistro: String?,
    val fechaExpedicionDoc: String?,
    val fechaExpiracionDoc: String?,
    val fechaNacimiento: String?,
    val isoCode: String?,
    val lugarNacimiento: String?,
    val mrzCode: String?,
    val numeroDocumento: String?,
    val numeroEmision: String?,
    val numeroPersonal: String?,
    val ocrNumber: String?,
    val pais: String?,
    val primerApellido: String?,
    val primerNombre: String?,
    val rh: String?,
    val segundoApellido: String?,
    val segundoNombre: String?,
    val sexo: String?,
    val subTipo: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(`class`)
        parcel.writeString(claveElector)
        parcel.writeString(codigoCic)
        parcel.writeString(documentType)
        parcel.writeString(documentTypeDesc)
        parcel.writeValue(edad)
        parcel.writeString(fechaDeRegistro)
        parcel.writeString(fechaExpedicionDoc)
        parcel.writeString(fechaExpiracionDoc)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(isoCode)
        parcel.writeString(lugarNacimiento)
        parcel.writeString(mrzCode)
        parcel.writeString(numeroDocumento)
        parcel.writeString(numeroEmision)
        parcel.writeString(numeroPersonal)
        parcel.writeString(ocrNumber)
        parcel.writeString(pais)
        parcel.writeString(primerApellido)
        parcel.writeString(primerNombre)
        parcel.writeString(rh)
        parcel.writeString(segundoApellido)
        parcel.writeString(segundoNombre)
        parcel.writeString(sexo)
        parcel.writeString(subTipo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}
