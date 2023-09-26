
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class ConsultarConvenio implements Parcelable {

    @SerializedName(value = "estadoActivo", alternate = {"EstadoActivo"})
    @Expose
    private boolean estadoActivo;
    @SerializedName(value = "aTDP", alternate = {"ATDP"})
    @Expose
    private String aTDP;
    @SerializedName(value = "serviciosConv", alternate = {"ServiciosConv"})
    @Expose
    private List<ServiciosConv> serviciosConv =  Collections.emptyList();
    @SerializedName("personalizacion")
    @Expose
    private List<Personalizacion> personalizacion = Collections.emptyList();
    @SerializedName(value = "respuestaTransaccion", alternate = {"RespuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Parcelable.Creator<ConsultarConvenio> CREATOR = new Creator<ConsultarConvenio>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ConsultarConvenio createFromParcel(Parcel in) {
            return new ConsultarConvenio(in);
        }

        public ConsultarConvenio[] newArray(int size) {
            return (new ConsultarConvenio[size]);
        }

    };

    protected ConsultarConvenio(Parcel in) {
        this.estadoActivo = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.aTDP = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.serviciosConv, (ServiciosConv.class.getClassLoader()));
        in.readList(this.personalizacion, (Personalizacion.class.getClassLoader()));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ConsultarConvenio() {
    }

    /**
     * @param respuestaTransaccion
     * @param aTDP
     * @param serviciosConv
     * @param estadoActivo
     */
    public ConsultarConvenio(boolean estadoActivo, String aTDP, List<ServiciosConv> serviciosConv, List<Personalizacion> personalizacion, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.estadoActivo = estadoActivo;
        this.aTDP = aTDP;
        this.serviciosConv = serviciosConv;
        this.personalizacion = personalizacion;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public String getATDP() {
        return aTDP;
    }

    public void setATDP(String aTDP) {
        this.aTDP = aTDP;
    }

    public List<ServiciosConv> getServiciosConv() {
        if (serviciosConv == null) {
            return Collections.emptyList();
        }
        return serviciosConv;
    }

    public void setServiciosConv(List<ServiciosConv> serviciosConv) {
        this.serviciosConv = serviciosConv;
    }

    public List<Personalizacion> getPersonalizacion() {
        return personalizacion;
    }

    public void setPersonalizacion(List<Personalizacion> personalizacion) {
        this.personalizacion = personalizacion;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(estadoActivo);
        dest.writeValue(aTDP);
        dest.writeList(serviciosConv);
        dest.writeList(personalizacion);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}