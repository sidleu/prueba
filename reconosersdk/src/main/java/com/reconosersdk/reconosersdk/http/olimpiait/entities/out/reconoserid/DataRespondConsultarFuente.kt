package com.reconosersdk.reconosersdk.http.olimpiait.entities.out.reconoserid

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataRespondConsultarFuente(
    @SerializedName("existe", alternate = ["Existe"])
    var existe: Boolean = false,
    @SerializedName("listaImagenes", alternate = ["ListaImagenes"])
    var listaImagenes: List<ListaImagenes> = listOf()
) : Parcelable