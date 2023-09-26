package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosOTP implements Parcelable {

    @SerializedName(value = "tipoOTP", alternate = {"TipoOTP"})
    @Expose
    private String tipoOTP;

    @SerializedName(value = "mensaje", alternate = {"Mensaje"})
    @Expose
    private String mensaje;

    public DatosOTP() {
    }

    public DatosOTP(String tipoOTP, String mensaje) {
        this.tipoOTP = tipoOTP;
        this.mensaje = mensaje;
    }

    public String getTipoOTP() {
        return tipoOTP;
    }

    public void setTipoOTP(String tipoOTP) {
        this.tipoOTP = tipoOTP;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tipoOTP);
        dest.writeString(this.mensaje);
    }

    protected DatosOTP(Parcel in) {
        this.tipoOTP = in.readString();
        this.mensaje = in.readString();
    }

    public static final Parcelable.Creator<DatosOTP> CREATOR = new Parcelable.Creator<DatosOTP>() {
        @Override
        public DatosOTP createFromParcel(Parcel source) {
            return new DatosOTP(source);
        }

        @Override
        public DatosOTP[] newArray(int size) {
            return new DatosOTP[size];
        }
    };
}
