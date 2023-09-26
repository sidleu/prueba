package com.reconosersdk.reconosersdk.http.olimpiait.entities.`in`

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DocumentValidations(
    @SerializedName(value = "validation", alternate = ["Validation"])
    @Expose
    var validation: List<Validations>? = emptyList(),

    @SerializedName(value = "validation_tested", alternate = ["Validation_tested"])
    @Expose
    var validation_tested: String? = "",

    @SerializedName(value = "total", alternate = ["Total"])
    @Expose
    var total: String? = "-1"

) : Parcelable {

    constructor(parcel: Parcel) : this() {
        validation = parcel.createTypedArrayList(Validations)
        validation_tested = parcel.readString()
        total = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(validation)
        parcel.writeString(validation_tested)
        parcel.writeString(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DocumentValidations> {
        override fun createFromParcel(parcel: Parcel): DocumentValidations {
            return DocumentValidations(parcel)
        }

        override fun newArray(size: Int): Array<DocumentValidations?> {
            return arrayOfNulls(size)
        }
    }
}