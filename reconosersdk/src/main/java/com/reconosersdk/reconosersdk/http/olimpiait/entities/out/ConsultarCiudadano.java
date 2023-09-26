
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConsultarCiudadano implements Parcelable {

    @SerializedName(value = "Ciudadano", alternate = {"ciudadano"})
    @Expose
    private Ciudadano ciudadano;
    @SerializedName(value = "Status", alternate = {"status"})
    @Expose
    private List<StatusCiudadano> status = new ArrayList<>();
    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Parcelable.Creator<ConsultarCiudadano> CREATOR = new Creator<ConsultarCiudadano>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ConsultarCiudadano createFromParcel(Parcel in) {
            return new ConsultarCiudadano(in);
        }

        public ConsultarCiudadano[] newArray(int size) {
            return (new ConsultarCiudadano[size]);
        }
    };

    protected ConsultarCiudadano(Parcel in) {
        this.ciudadano = ((Ciudadano) in.readValue((Ciudadano.class.getClassLoader())));
        in.readList(this.status, (StatusCiudadano.class.getClassLoader()));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ConsultarCiudadano() {
    }

    /**
     * @param respuestaTransaccion
     * @param ciudadano
     * @param status
     */
    public ConsultarCiudadano(Ciudadano ciudadano, List<StatusCiudadano> status, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.ciudadano = ciudadano;
        this.status = status;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    public List<StatusCiudadano> getStatus() {
        if (status == null) {
            return Collections.emptyList();
        }
        return status;
    }

    public void setStatus(List<StatusCiudadano> status) {
        this.status = status;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("ciudadano", ciudadano).append("status", status).append("respuestaTransaccion", respuestaTransaccion).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ciudadano);
        dest.writeList(status);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
