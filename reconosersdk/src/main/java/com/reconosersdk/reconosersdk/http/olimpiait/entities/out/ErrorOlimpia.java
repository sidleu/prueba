
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ErrorOlimpia implements Parcelable {

    @SerializedName("EsValida")
    @Expose
    private Boolean esValida;
    @SerializedName("RespuestaTransaccion")
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Parcelable.Creator<ErrorOlimpia> CREATOR = new Creator<ErrorOlimpia>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ErrorOlimpia createFromParcel(Parcel in) {
            return new ErrorOlimpia(in);
        }

        public ErrorOlimpia[] newArray(int size) {
            return (new ErrorOlimpia[size]);
        }
    };

    protected ErrorOlimpia(Parcel in) {
        this.esValida = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    public ErrorOlimpia() {
    }

    public Boolean getEsValida() {
        return esValida;
    }

    public void setEsValida(Boolean esValida) {
        this.esValida = esValida;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("esValida", esValida).append("respuestaTransaccion", respuestaTransaccion).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(esValida);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
