
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConsultarEstadoProcesoOut implements Parcelable {

    @SerializedName(value = "Estados", alternate = {"estados"})
    @Expose
    private List<String> estados = new ArrayList<>();
    @SerializedName(value = "Values", alternate = {"values"})
    @Expose
    private List<Value> values = new ArrayList<>();
    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Creator<ConsultarEstadoProcesoOut> CREATOR = new Creator<ConsultarEstadoProcesoOut>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ConsultarEstadoProcesoOut createFromParcel(Parcel in) {
            return new ConsultarEstadoProcesoOut(in);
        }

        public ConsultarEstadoProcesoOut[] newArray(int size) {
            return (new ConsultarEstadoProcesoOut[size]);
        }
    };

    protected ConsultarEstadoProcesoOut(Parcel in) {
        in.readList(this.estados, (String.class.getClassLoader()));
        in.readList(this.values, (Value.class.getClassLoader()));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ConsultarEstadoProcesoOut() {
    }

    /**
     * @param respuestaTransaccion
     * @param values
     * @param estados
     */
    public ConsultarEstadoProcesoOut(List<String> estados, List<Value> values, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.estados = estados;
        this.values = values;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public List<String> getEstados() {
        if (estados == null) {
            return Collections.emptyList();
        }
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<Value> getValues() {
        if (values == null) {
            return Collections.emptyList();
        }
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(estados);
        dest.writeList(values);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
