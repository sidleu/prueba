package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcel
import android.os.Parcelable

data class Validations(
    val typeValidation: String?,
    val result: Boolean? = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(typeValidation)
        parcel.writeValue(result)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Validations> {
        override fun createFromParcel(parcel: Parcel): Validations {
            return Validations(parcel)
        }

        override fun newArray(size: Int): Array<Validations?> {
            return arrayOfNulls(size)
        }
    }

}