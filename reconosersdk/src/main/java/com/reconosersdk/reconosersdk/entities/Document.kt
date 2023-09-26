package com.reconosersdk.reconosersdk.entities

import android.os.Parcel
import android.os.Parcelable
import com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`.DocumentValidations

data class Document(
    val documentoAnverso: DocumentoAnverso?,
    val documentoReverso: DocumentoReverso?,
    val path: String?,
    val typeDocument: String?,
    val stateDocument: String?,
    var textScan: DataResult?,
    var documentValidations: DocumentValidations? = DocumentValidations()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(DocumentoAnverso::class.java.classLoader),
        parcel.readParcelable(DocumentoReverso::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(DataResult::class.java.classLoader),
        parcel.readParcelable(DocumentValidations::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(documentoAnverso, flags)
        parcel.writeParcelable(documentoReverso, flags)
        parcel.writeString(path)
        parcel.writeString(typeDocument)
        parcel.writeString(stateDocument)
        parcel.writeParcelable(textScan, flags)
        parcel.writeParcelable(documentValidations, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Document> {
        override fun createFromParcel(parcel: Parcel): Document {
            return Document(parcel)
        }

        override fun newArray(size: Int): Array<Document?> {
            return arrayOfNulls(size)
        }
    }
}

