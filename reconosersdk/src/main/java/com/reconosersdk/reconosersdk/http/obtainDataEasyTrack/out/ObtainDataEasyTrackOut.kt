package com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.out


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ObtainDataEasyTrackOut(
    @SerializedName(value = "code", alternate = ["Code"])
    @Expose
    var code: Int? =0,
    @SerializedName(value = "codeName", alternate = ["CodeName"])
    @Expose
    var codeName: String?= "",
    @SerializedName(value = "data", alternate = ["Data"])
    @Expose
    var `data`: Data? = Data()
) : Parcelable{
    constructor() : this(0,"",Data())
}