package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class ValidarRespuestaIn implements Parcelable {

    @SerializedName(value = "GuidCiudadano", alternate = {"guidCiudadano"})
    @Expose
    private String guidCiudadano;

    @SerializedName(value = "IdCuestionario", alternate = {"idCuestionario"})
    @Expose
    private String idCuestionario;

    @SerializedName(value = "RegistroCuestionario", alternate = {"registroCuestionario"})
    @Expose
    private int registroCuestionario;

    @SerializedName(value = "Respuestas", alternate = {"respuestas"})
    @Expose
    private List<RespuestasIn> respuestas;

    public ValidarRespuestaIn() {
    }

    public ValidarRespuestaIn(String guidCiudadano, String idCuestionario, int registroCuestionario, List<RespuestasIn> respuestas) {
        this.guidCiudadano = guidCiudadano;
        this.idCuestionario = idCuestionario;
        this.registroCuestionario = registroCuestionario;
        this.respuestas = respuestas;
    }

    public String getGuidCiudadano() {
        return guidCiudadano;
    }

    public void setGuidCiudadano(String guidCiudadano) {
        this.guidCiudadano = guidCiudadano;
    }

    public String getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(String idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public int getRegistroCuestionario() {
        return registroCuestionario;
    }

    public void setRegistroCuestionario(int registroCuestionario) {
        this.registroCuestionario = registroCuestionario;
    }

    public List<RespuestasIn> getRespuestas() {
        if (respuestas == null) {
            return Collections.emptyList();
        }
        return respuestas;
    }

    public void setRespuestas(List<RespuestasIn> respuestas) {
        this.respuestas = respuestas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.guidCiudadano);
        dest.writeString(this.idCuestionario);
        dest.writeInt(this.registroCuestionario);
        dest.writeTypedList(this.respuestas);
    }

    protected ValidarRespuestaIn(Parcel in) {
        this.guidCiudadano = in.readString();
        this.idCuestionario = in.readString();
        this.registroCuestionario = in.readInt();
        this.respuestas = in.createTypedArrayList(RespuestasIn.CREATOR);
    }

    public static final Parcelable.Creator<ValidarRespuestaIn> CREATOR = new Parcelable.Creator<ValidarRespuestaIn>() {
        @Override
        public ValidarRespuestaIn createFromParcel(Parcel source) {
            return new ValidarRespuestaIn(source);
        }

        @Override
        public ValidarRespuestaIn[] newArray(int size) {
            return new ValidarRespuestaIn[size];
        }
    };
}
