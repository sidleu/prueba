
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Ciudadano implements Parcelable {

    @SerializedName(value = "GuidCiu", alternate = {"guidCiu"})
    @Expose
    private String guidCiu;
    @SerializedName(value = "GuidConv", alternate = {"guidConv"})
    @Expose
    private String guidConv;
    @SerializedName(value = "TipoDoc", alternate = {"tipoDoc"})
    @Expose
    private String tipoDoc;
    @SerializedName(value = "NumDoc", alternate = {"numDoc"})
    @Expose
    private String numDoc;
    @SerializedName(value = "Email", alternate = {"email"})
    @Expose
    private String email;
    @SerializedName(value = "CodPais", alternate = {"codPais"})
    @Expose
    private String codPais;
    @SerializedName(value = "Celular", alternate = {"celular"})
    @Expose
    private String celular;
    @SerializedName(value = "DatosAdi", alternate = {"datosAdi"})
    @Expose
    private String datosAdi;
    public final static Parcelable.Creator<Ciudadano> CREATOR = new Creator<Ciudadano>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Ciudadano createFromParcel(Parcel in) {
            return new Ciudadano(in);
        }

        public Ciudadano[] newArray(int size) {
            return (new Ciudadano[size]);
        }
    };

    protected Ciudadano(Parcel in) {
        this.guidCiu = ((String) in.readValue((String.class.getClassLoader())));
        this.guidConv = ((String) in.readValue((String.class.getClassLoader())));
        this.tipoDoc = ((String) in.readValue((String.class.getClassLoader())));
        this.numDoc = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.codPais = ((String) in.readValue((String.class.getClassLoader())));
        this.celular = ((String) in.readValue((String.class.getClassLoader())));
        this.datosAdi = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Ciudadano() {
    }

    /**
     * @param numDoc
     * @param datosAdi
     * @param tipoDoc
     * @param codPais
     * @param email
     * @param guidCiu
     * @param celular
     * @param guidConv
     */
    public Ciudadano(String guidCiu, String guidConv, String tipoDoc, String numDoc, String email, String codPais, String celular, String datosAdi) {
        super();
        this.guidCiu = guidCiu;
        this.guidConv = guidConv;
        this.tipoDoc = tipoDoc;
        this.numDoc = numDoc;
        this.email = email;
        this.codPais = codPais;
        this.celular = celular;
        this.datosAdi = datosAdi;
    }

    public String getGuidCiu() {
        return guidCiu;
    }

    public void setGuidCiu(String guidCiu) {
        this.guidCiu = guidCiu;
    }

    public String getGuidConv() {
        return guidConv;
    }

    public void setGuidConv(String guidConv) {
        this.guidConv = guidConv;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDatosAdi() {
        return datosAdi;
    }

    public void setDatosAdi(String datosAdi) {
        this.datosAdi = datosAdi;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("guidCiu", guidCiu).append("guidConv", guidConv).append("tipoDoc", tipoDoc).append("numDoc", numDoc).append("email", email).append("codPais", codPais).append("celular", celular).append("datosAdi", datosAdi).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(guidCiu);
        dest.writeValue(guidConv);
        dest.writeValue(tipoDoc);
        dest.writeValue(numDoc);
        dest.writeValue(email);
        dest.writeValue(codPais);
        dest.writeValue(celular);
        dest.writeValue(datosAdi);
    }

    public int describeContents() {
        return 0;
    }
}
