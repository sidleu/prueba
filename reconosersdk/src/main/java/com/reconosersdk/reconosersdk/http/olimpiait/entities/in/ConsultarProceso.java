
package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultarProceso implements Parcelable {

    @SerializedName(value = "guidConv", alternate = {"GuidConv"})
    @Expose
    private String guidConv;
    @SerializedName(value = "procesoConvenioGuid", alternate = {"ProcesoConvenioGuid"})
    @Expose
    private String procesoConvenioGuid;
    @SerializedName(value = "codigoCliente", alternate = {"CodigoCliente"})
    @Expose
    private String codigoCliente;
    public final static Creator<ConsultarProceso> CREATOR = new Creator<ConsultarProceso>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ConsultarProceso createFromParcel(Parcel in) {
            return new ConsultarProceso(in);
        }

        public ConsultarProceso[] newArray(int size) {
            return (new ConsultarProceso[size]);
        }

    };

    protected ConsultarProceso(Parcel in) {
        this.guidConv = ((String) in.readValue((String.class.getClassLoader())));
        this.procesoConvenioGuid = ((String) in.readValue((String.class.getClassLoader())));
        this.codigoCliente = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ConsultarProceso() {
    }

    /**
     * @param codigoCliente
     * @param procesoConvenioGuid
     * @param guidConv
     */
    public ConsultarProceso(String guidConv, String procesoConvenioGuid, String codigoCliente) {
        super();
        this.guidConv = guidConv;
        this.procesoConvenioGuid = procesoConvenioGuid;
        this.codigoCliente = codigoCliente;
    }

    public String getGuidConv() {
        return guidConv;
    }

    public void setGuidConv(String guidConv) {
        this.guidConv = guidConv;
    }

    public String getProcesoConvenioGuid() {
        return procesoConvenioGuid;
    }

    public void setProcesoConvenioGuid(String procesoConvenioGuid) {
        this.procesoConvenioGuid = procesoConvenioGuid;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(guidConv);
        dest.writeValue(procesoConvenioGuid);
        dest.writeValue(codigoCliente);
    }

    public int describeContents() {
        return 0;
    }
}

