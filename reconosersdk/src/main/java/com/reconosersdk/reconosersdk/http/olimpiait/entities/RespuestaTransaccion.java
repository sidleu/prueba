
package com.reconosersdk.reconosersdk.http.olimpiait.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class RespuestaTransaccion implements Parcelable {

    @SerializedName("EsExitosa")
    @Expose
    private boolean esExitosa;
    @SerializedName("ErrorEntransaccion")
    @Expose
    private List<ErrorEntransaccion> errorEntransaccion = null;
    public final static Parcelable.Creator<RespuestaTransaccion> CREATOR = new Creator<RespuestaTransaccion>() {

        @SuppressWarnings({
                "unchecked"
        })
        public RespuestaTransaccion createFromParcel(Parcel in) {
            return new RespuestaTransaccion(in);
        }

        public RespuestaTransaccion[] newArray(int size) {
            return (new RespuestaTransaccion[size]);
        }
    };

    protected RespuestaTransaccion(Parcel in) {
        this.esExitosa = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.errorEntransaccion, (com.reconosersdk.reconosersdk.http.olimpiait.entities.ErrorEntransaccion.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public RespuestaTransaccion() {
    }

    /**
     * @param esExitosa
     * @param errorEntransaccion
     */
    public RespuestaTransaccion(boolean esExitosa, List<ErrorEntransaccion> errorEntransaccion) {
        super();
        this.esExitosa = esExitosa;
        this.errorEntransaccion = errorEntransaccion;
    }

    public boolean isEsExitosa() {
        return esExitosa;
    }

    public void setEsExitosa(boolean esExitosa) {
        this.esExitosa = esExitosa;
    }

    public List<ErrorEntransaccion> getErrorEntransaccion() {
        if (errorEntransaccion == null) {
            return new ArrayList<>();
        }
        return errorEntransaccion;
    }

    public void setErrorEntransaccion(List<ErrorEntransaccion> errorEntransaccion) {
        this.errorEntransaccion = errorEntransaccion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("esExitosa", esExitosa).append("errorEntransaccion", errorEntransaccion).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(esExitosa);
        dest.writeList(errorEntransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
