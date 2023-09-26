package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelarProcesoOut implements Parcelable {

    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Creator<CancelarProcesoOut> CREATOR = new Creator<CancelarProcesoOut>() {

        @SuppressWarnings({
                "unchecked"
        })
        public CancelarProcesoOut createFromParcel(Parcel in) {
            return new CancelarProcesoOut(in);
        }

        public CancelarProcesoOut[] newArray(int size) {
            return (new CancelarProcesoOut[size]);
        }
    };

    protected CancelarProcesoOut(Parcel in) {
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public CancelarProcesoOut() {
    }

    /**
     * @param respuestaTransaccion
     */
    public CancelarProcesoOut(RespuestaTransaccion respuestaTransaccion) {
        super();
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
