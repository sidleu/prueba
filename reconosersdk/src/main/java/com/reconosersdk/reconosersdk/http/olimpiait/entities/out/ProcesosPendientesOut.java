
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcesosPendientesOut implements Parcelable {

    @SerializedName(value = "DatosProceso", alternate = {"datosProceso"})
    @Expose
    private List<DatosProceso> datosProceso = new ArrayList<>();
    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Creator<ProcesosPendientesOut> CREATOR = new Creator<ProcesosPendientesOut>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ProcesosPendientesOut createFromParcel(Parcel in) {
            return new ProcesosPendientesOut(in);
        }

        public ProcesosPendientesOut[] newArray(int size) {
            return (new ProcesosPendientesOut[size]);
        }
    };

    protected ProcesosPendientesOut(Parcel in) {
        in.readList(this.datosProceso, (DatosProceso.class.getClassLoader()));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ProcesosPendientesOut() {
    }

    /**
     * @param respuestaTransaccion
     * @param datosProceso
     */
    public ProcesosPendientesOut(List<DatosProceso> datosProceso, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.datosProceso = datosProceso;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public List<DatosProceso> getDatosProceso() {
        if (datosProceso == null) {
            return Collections.emptyList();
        }
        return datosProceso;
    }

    public void setDatosProceso(List<DatosProceso> datosProceso) {
        this.datosProceso = datosProceso;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(datosProceso);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
