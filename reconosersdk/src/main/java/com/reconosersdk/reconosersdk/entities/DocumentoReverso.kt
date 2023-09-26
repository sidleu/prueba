package com.reconosersdk.reconosersdk.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DocumentoReverso(
    @SerializedName(value = "stringReverso", alternate = ["StringReverso"])
    @Expose
    var stringReverso: String? = "",
    @SerializedName(value = "stringBarcode", alternate = ["StringBarcode"])
    @Expose
    var stringBarcode: String? = ""

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    fun setDocumentoReverso(stringReverso: String, stringBarcode: String) {
        this.stringReverso = stringReverso
        this.stringBarcode = stringBarcode
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(stringReverso)
        parcel.writeString(stringBarcode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DocumentoReverso> {
        override fun createFromParcel(parcel: Parcel): DocumentoReverso {
            return DocumentoReverso(parcel)
        }

        override fun newArray(size: Int): Array<DocumentoReverso?> {
            return arrayOfNulls(size)
        }

        //Simple singleton
        private var instance: DocumentoReverso? = null

        fun getInstance(): DocumentoReverso? {
            if (instance == null) {
                instance = DocumentoReverso()
            }
            return instance
        }
    }
}

