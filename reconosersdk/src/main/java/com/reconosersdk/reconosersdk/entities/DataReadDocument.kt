package com.reconosersdk.reconosersdk.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DataReadDocument(
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("value")
    val value: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataReadDocument> {
        override fun createFromParcel(parcel: Parcel): DataReadDocument {
            return DataReadDocument(parcel)
        }

        override fun newArray(size: Int): Array<DataReadDocument?> {
            return arrayOfNulls(size)
        }
    }
}