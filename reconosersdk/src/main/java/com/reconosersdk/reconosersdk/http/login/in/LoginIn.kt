package com.reconosersdk.reconosersdk.http.login.`in`


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


/**
 ** Object for the service to LoginIn
 */

@Parcelize
data class LoginIn(
     @SerializedName(value = "email", alternate = ["Email"])
    @Expose
    var email: String? = "",
     @SerializedName(value = "password", alternate = ["Password"])
    @Expose
    var password: String? = ""
) : Parcelable{
    constructor() : this("",  "")
}