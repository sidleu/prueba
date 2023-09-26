package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ValidateFederalDriverLicenseOut(
    @SerializedName(value = "card", alternate = ["Card"])
    var card: Card?,
    @SerializedName(value = "trasaccion", alternate = ["Trasaccion"])
    var trasaccion: Trasaccion?
) : Parcelable {
    constructor() : this(Card(), Trasaccion())
}