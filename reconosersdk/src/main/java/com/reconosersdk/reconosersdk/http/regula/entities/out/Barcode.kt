package com.reconosersdk.reconosersdk.http.regula.entities.out

import android.os.Parcel
import android.os.Parcelable

data class Barcode(
    val descripcion: String?,
    val idType: String?,
    val valor: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(descripcion)
        parcel.writeString(idType)
        parcel.writeString(valor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Barcode> {
        override fun createFromParcel(parcel: Parcel): Barcode {
            return Barcode(parcel)
        }

        override fun newArray(size: Int): Array<Barcode?> {
            return arrayOfNulls(size)
        }
    }
}