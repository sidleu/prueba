package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestasIn implements Parcelable {

    @SerializedName(value = "IdPregunta", alternate = {"idPregunta"})
    @Expose
    private String idPregunta;

    @SerializedName(value = "IdRespuesta", alternate = {"idRespuesta"})
    @Expose
    private String idRespuesta;

    public RespuestasIn() {
    }

    public RespuestasIn(String idPregunta, String idRespuesta) {
        this.idPregunta = idPregunta;
        this.idRespuesta = idRespuesta;
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPregunta);
        dest.writeString(this.idRespuesta);
    }

    protected RespuestasIn(Parcel in) {
        this.idPregunta = in.readString();
        this.idRespuesta = in.readString();
    }

    public static final Parcelable.Creator<RespuestasIn> CREATOR = new Parcelable.Creator<RespuestasIn>() {
        @Override
        public RespuestasIn createFromParcel(Parcel source) {
            return new RespuestasIn(source);
        }

        @Override
        public RespuestasIn[] newArray(int size) {
            return new RespuestasIn[size];
        }
    };
}
