package com.reconosersdk.reconosersdk.http.regula.entities.out

import android.os.Parcel
import android.os.Parcelable

data class Ocr(
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

    companion object CREATOR : Parcelable.Creator<Ocr> {
        override fun createFromParcel(parcel: Parcel): Ocr {
            return Ocr(parcel)
        }

        override fun newArray(size: Int): Array<Ocr?> {
            return arrayOfNulls(size)
        }
    }

}