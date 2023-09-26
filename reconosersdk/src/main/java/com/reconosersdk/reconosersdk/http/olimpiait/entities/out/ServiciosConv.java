
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiciosConv implements Parcelable {

    @SerializedName(value = "idServicio", alternate = {"IdServicio"})
    @Expose
    private int idServicio;
    @SerializedName(value = "nombreServicio", alternate = {"NombreServicio"})
    @Expose
    private String nombreServicio;
    @SerializedName(value = "tipoServicio", alternate = {"TipoServicio"})
    @Expose
    private String tipoServicio;
    @SerializedName(value = "subTipo", alternate = {"SubTipo"})
    @Expose
    private String subTipo;
    @SerializedName(value = "nivelCaptura", alternate = {"NivelCaptura"})
    @Expose
    private int nivelCaptura;
    @SerializedName(value = "metodoCaptura", alternate = {"MetodoCaptura"})
    @Expose
    private int metodoCaptura;
    @SerializedName(value = "continuarProceso", alternate = {"ContinuarProceso"})
    @Expose
    private boolean continuarProceso;
    @SerializedName(value = "reintentos", alternate = {"Reintentos"})
    @Expose
    private int reintentos;
    @SerializedName(value = "esperaReintentos", alternate = {"EsperaReintentos"})
    @Expose
    private int esperaReintentos;
    public final static Parcelable.Creator<ServiciosConv> CREATOR = new Creator<ServiciosConv>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ServiciosConv createFromParcel(Parcel in) {
            return new ServiciosConv(in);
        }

        public ServiciosConv[] newArray(int size) {
            return (new ServiciosConv[size]);
        }
    };

    protected ServiciosConv(Parcel in) {
        this.idServicio = ((int) in.readValue((int.class.getClassLoader())));
        this.nombreServicio = ((String) in.readValue((String.class.getClassLoader())));
        this.tipoServicio = ((String) in.readValue((String.class.getClassLoader())));
        this.subTipo = ((String) in.readValue((String.class.getClassLoader())));
        this.nivelCaptura = ((int) in.readValue((int.class.getClassLoader())));
        this.metodoCaptura = ((int) in.readValue((int.class.getClassLoader())));
        this.continuarProceso = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.reintentos = ((int) in.readValue((int.class.getClassLoader())));
        this.esperaReintentos = ((int) in.readValue((int.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ServiciosConv() {
    }

    /**
     * @param reintentos
     * @param continuarProceso
     * @param nivelCaptura
     * @param metodoCaptura
     * @param tipoServicio
     * @param nombreServicio
     * @param idServicio
     * @param subTipo
     * @param esperaReintentos
     */
    public ServiciosConv(int idServicio, String nombreServicio, String tipoServicio, String subTipo, int nivelCaptura, int metodoCaptura, boolean continuarProceso, int reintentos, int esperaReintentos) {
        super();
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.tipoServicio = tipoServicio;
        this.subTipo = subTipo;
        this.nivelCaptura = nivelCaptura;
        this.metodoCaptura = metodoCaptura;
        this.continuarProceso = continuarProceso;
        this.reintentos = reintentos;
        this.esperaReintentos = esperaReintentos;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public int getNivelCaptura() {
        return nivelCaptura;
    }

    public void setNivelCaptura(int nivelCaptura) {
        this.nivelCaptura = nivelCaptura;
    }

    public int getMetodoCaptura() {
        return metodoCaptura;
    }

    public void setMetodoCaptura(int metodoCaptura) {
        this.metodoCaptura = metodoCaptura;
    }

    public boolean isContinuarProceso() {
        return continuarProceso;
    }

    public void setContinuarProceso(boolean continuarProceso) {
        this.continuarProceso = continuarProceso;
    }

    public int getReintentos() {
        return reintentos;
    }

    public void setReintentos(int reintentos) {
        this.reintentos = reintentos;
    }

    public int getEsperaReintentos() {
        return esperaReintentos;
    }

    public void setEsperaReintentos(int esperaReintentos) {
        this.esperaReintentos = esperaReintentos;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idServicio);
        dest.writeValue(nombreServicio);
        dest.writeValue(tipoServicio);
        dest.writeValue(subTipo);
        dest.writeValue(nivelCaptura);
        dest.writeValue(metodoCaptura);
        dest.writeValue(continuarProceso);
        dest.writeValue(reintentos);
        dest.writeValue(esperaReintentos);
    }

    public int describeContents() {
        return 0;
    }
}