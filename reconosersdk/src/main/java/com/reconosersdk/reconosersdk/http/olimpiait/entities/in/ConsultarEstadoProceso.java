package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultarEstadoProceso implements Parcelable {

    @SerializedName(value = "GuidConv", alternate = {"guidConv"})
    @Expose
    private String guidConv;
    @SerializedName(value = "Asesor", alternate = {"asesor"})
    @Expose
    private String asesor;
    @SerializedName(value = "FechaInicial", alternate = {"fechaInicial"})
    @Expose
    private String fechaInicial;
    @SerializedName(value = "FechaFinal", alternate = {"fechaFinal"})
    @Expose
    private String fechaFinal;
    public final static Creator<ConsultarEstadoProceso> CREATOR = new Creator<ConsultarEstadoProceso>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ConsultarEstadoProceso createFromParcel(Parcel in) {
            return new ConsultarEstadoProceso(in);
        }

        public ConsultarEstadoProceso[] newArray(int size) {
            return (new ConsultarEstadoProceso[size]);
        }
    };

    protected ConsultarEstadoProceso(Parcel in) {
        this.guidConv = ((String) in.readValue((String.class.getClassLoader())));
        this.asesor = ((String) in.readValue((String.class.getClassLoader())));
        this.fechaInicial = ((String) in.readValue((String.class.getClassLoader())));
        this.fechaFinal = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ConsultarEstadoProceso() {
    }

    /**
     * @param fechaFinal
     * @param fechaInicial
     * @param asesor
     * @param guidConv
     */
    public ConsultarEstadoProceso(String guidConv, String asesor, String fechaInicial, String fechaFinal) {
        super();
        this.guidConv = guidConv;
        this.asesor = asesor;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
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

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(guidConv);
        dest.writeValue(asesor);
        dest.writeValue(fechaInicial);
        dest.writeValue(fechaFinal);
    }

    public int describeContents() {
        return 0;
    }
}