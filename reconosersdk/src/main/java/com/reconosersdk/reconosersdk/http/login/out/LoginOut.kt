package com.reconosersdk.reconosersdk.http.login.out


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Object for the service to LoginOut
 */

@Parcelize
data class LoginOut(
    @SerializedName(value = "accessToken", alternate = ["AccessToken"])
    @Expose
    var accessToken: String? = "",
    @SerializedName(value = "errorDescription", alternate = ["ErrorDescription"])
    @Expose
    var errorDescription: String? = "",
    @SerializedName(value = "expiresIn", alternate = ["ExpiresIn"])
    @Expose
    var expiresIn: Int? = 0,
    @SerializedName(value = "identityToken", alternate = ["IdentityToken"])
    @Expose
    var identityToken: String? = "",
    @SerializedName(value = "refreshToken", alternate = ["RefreshToken"])
    @Expose
    var refreshToken: String? = "",
    @SerializedName(value = "tokenType", alternate = ["TokenType"])
    @Expose
    var tokenType: String? = "",
    @SerializedName(value = "error", alternate = ["Error"])
    @Expose
    var error: String? = ""
) : Serializable, Parcelable {
    constructor() : this("", "", 0, "", "", "", "")
}
