package com.reconosersdk.reconosersdk.http.olimpiait.entities.out

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    @SerializedName(value = "docProcess", alternate = ["DocProcess"])
    var docProcess: String? = "",
    @SerializedName(value = "estado", alternate = ["Estado"])
    var estado: String? = "",
    @SerializedName(value = "score", alternate = ["Score"])
    var score: Int? = 0
): Parcelable {
    constructor() : this("",  "", 0)
}