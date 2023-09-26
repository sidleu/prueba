package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelarProceso implements Parcelable {

    @SerializedName(value = "procesoConvenioGuid", alternate = {"ProcesoConvenioGuid"})
    @Expose
    private String procesoConvenioGuid;
    @SerializedName(value = "asesor", alternate = {"Asesor"})
    @Expose
    private String asesor;
    @SerializedName(value = "motivo", alternate = {"Motivo"})
    @Expose
    private String motivo;
    public final static Creator<CancelarProceso> CREATOR = new Creator<CancelarProceso>() {

        @SuppressWarnings({
                "unchecked"
        })
        public CancelarProceso createFromParcel(Parcel in) {
            return new CancelarProceso(in);
        }

        public CancelarProceso[] newArray(int size) {
            return (new CancelarProceso[size]);
        }

    };

    protected CancelarProceso(Parcel in) {
        this.procesoConvenioGuid = ((String) in.readValue((String.class.getClassLoader())));
        this.asesor = ((String) in.readValue((String.class.getClassLoader())));
        this.motivo = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public CancelarProceso() {
    }

    /**
     * @param motivo
     * @param asesor
     * @param procesoConvenioGuid
     */
    public CancelarProceso(String procesoConvenioGuid, String asesor, String motivo) {
        super();
        this.procesoConvenioGuid = procesoConvenioGuid;
        this.asesor = asesor;
        this.motivo = motivo;
    }

    public String getProcesoConvenioGuid() {
        return procesoConvenioGuid;
    }

    public void setProcesoConvenioGuid(String procesoConvenioGuid) {
        this.procesoConvenioGuid = procesoConvenioGuid;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(procesoConvenioGuid);
        dest.writeValue(asesor);
        dest.writeValue(motivo);
    }

    public int describeContents() {
        return 0;
    }
}