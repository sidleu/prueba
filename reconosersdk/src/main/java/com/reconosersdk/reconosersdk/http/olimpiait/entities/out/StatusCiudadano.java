package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusCiudadano implements Parcelable {

    @SerializedName(value = "Servicio", alternate = {"servicio"})
    @Expose
    private String servicio;
    @SerializedName(value = "Tipo", alternate = {"tipo"})
    @Expose
    private String tipo;
    @SerializedName(value = "SubTipos", alternate = {"subTipos"})
    @Expose
    private String subTipos;
    @SerializedName(value = "Fecha", alternate = {"fecha"})
    @Expose
    private String fecha;
    @SerializedName(value = "Terminado", alternate = {"terminado"})
    @Expose
    private boolean terminado;
    public final static Parcelable.Creator<StatusCiudadano> CREATOR = new Creator<StatusCiudadano>() {

        @SuppressWarnings({
                "unchecked"
        })
        public StatusCiudadano createFromParcel(Parcel in) {
            return new StatusCiudadano(in);
        }

        public StatusCiudadano[] newArray(int size) {
            return (new StatusCiudadano[size]);
        }

    };

    protected StatusCiudadano(Parcel in) {
        this.servicio = ((String) in.readValue((String.class.getClassLoader())));
        this.tipo = ((String) in.readValue((String.class.getClassLoader())));
        this.subTipos = ((String) in.readValue((String.class.getClassLoader())));
        this.fecha = ((String) in.readValue((String.class.getClassLoader())));
        this.terminado = ((boolean) in.readValue((boolean.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public StatusCiudadano() {
    }

    /**
     * @param subTipos
     * @param servicio
     * @param fecha
     * @param tipo
     * @param terminado
     */
    public StatusCiudadano(String servicio, String tipo, String subTipos, String fecha, boolean terminado) {
        super();
        this.servicio = servicio;
        this.tipo = tipo;
        this.subTipos = subTipos;
        this.fecha = fecha;
        this.terminado = terminado;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubTipos() {
        return subTipos;
    }

    public void setSubTipos(String subTipos) {
        this.subTipos = subTipos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(servicio);
        dest.writeValue(tipo);
        dest.writeValue(subTipos);
        dest.writeValue(fecha);
        dest.writeValue(terminado);
    }

    public int describeContents() {
        return 0;
    }
}
