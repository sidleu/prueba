package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProcesosPendientes implements Parcelable {

    @SerializedName(value = "guidConv", alternate = {"GuidConv"})
    @Expose
    private String guidConv;
    @SerializedName(value = "asesor", alternate = {"Asesor"})
    @Expose
    private String asesor;
    @SerializedName(value = "sede", alternate = {"Sede"})
    @Expose
    private String sede;
    public final static Creator<ProcesosPendientes> CREATOR = new Creator<ProcesosPendientes>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ProcesosPendientes createFromParcel(Parcel in) {
            return new ProcesosPendientes(in);
        }

        public ProcesosPendientes[] newArray(int size) {
            return (new ProcesosPendientes[size]);
        }

    };

    protected ProcesosPendientes(Parcel in) {
        this.guidConv = ((String) in.readValue((String.class.getClassLoader())));
        this.asesor = ((String) in.readValue((String.class.getClassLoader())));
        this.sede = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ProcesosPendientes() {
    }

    /**
     * @param asesor
     * @param sede
     * @param guidConv
     */
    public ProcesosPendientes(String guidConv, String asesor, String sede) {
        super();
        this.guidConv = guidConv;
        this.asesor = asesor;
        this.sede = sede;
    }

    public String getGuidConv() {
        return guidConv;
    }

    public void setGuidConv(String guidConv) {
        this.guidConv = guidConv;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(guidConv);
        dest.writeValue(asesor);
        dest.writeValue(sede);
    }

    public int describeContents() {
        return 0;
    }
}