package com.reconosersdk.reconosersdk.http.openSource.out

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Object for the service to Breaches
 */

@Parcelize
data class Breaches(
    @SerializedName("breachDate", alternate = ["BreachDate"])
    @Expose
    var breachDate: String? = "",
    @SerializedName("addedDate", alternate = ["AddedDate"])
    @Expose
    var addedDate: String? = "",
    @SerializedName("dataClasses", alternate = ["DataClasses"])
    @Expose
    var dataClasses: List<String?> = emptyList(),
    @SerializedName("description", alternate = ["Description"])
    @Expose
    var description: String? = "",
    @SerializedName("domain", alternate = ["Domain"])
    @Expose
    var domain: String? = ""
) : Parcelable {
    constructor() : this("",  "", emptyList(),  "", "")
}