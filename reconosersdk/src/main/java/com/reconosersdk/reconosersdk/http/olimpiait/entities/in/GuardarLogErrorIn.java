package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuardarLogErrorIn implements Parcelable {

    @SerializedName(value = "GuidConv", alternate = {"guidConv"})
    @Expose
    private String guidConv;

    @SerializedName(value = "Texto", alternate = {"texto"})
    @Expose
    private String texto;

    @SerializedName(value = "Componente", alternate = {"componente"})
    @Expose
    private String componente;

    @SerializedName(value = "Usuario", alternate = {"usuario"})
    @Expose
    private String usuario;

    public GuardarLogErrorIn() {
    }

    public GuardarLogErrorIn(String guidConv, String texto, String componente, String usuario) {
        this.guidConv = guidConv;
        this.texto = texto;
        this.componente = componente;
        this.usuario = usuario;
    }

    public String getGuidConv() {
        return guidConv;
    }

    public void setGuidConv(String guidConv) {
        this.guidConv = guidConv;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.guidConv);
        dest.writeString(this.texto);
        dest.writeString(this.componente);
        dest.writeString(this.usuario);
    }

    protected GuardarLogErrorIn(Parcel in) {
        this.guidConv = in.readString();
        this.texto = in.readString();
        this.componente = in.readString();
        this.usuario = in.readString();
    }

    public static final Parcelable.Creator<GuardarLogErrorIn> CREATOR = new Parcelable.Creator<GuardarLogErrorIn>() {
        @Override
        public GuardarLogErrorIn createFromParcel(Parcel source) {
            return new GuardarLogErrorIn(source);
        }

        @Override
        public GuardarLogErrorIn[] newArray(int size) {
            return new GuardarLogErrorIn[size];
        }
    };
}
