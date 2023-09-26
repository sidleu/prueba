package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class GuardarLogError implements Parcelable {

    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Parcelable.Creator<GuardarLogError> CREATOR = new Creator<GuardarLogError>() {

        @SuppressWarnings({
                "unchecked"
        })
        public GuardarLogError createFromParcel(Parcel in) {
            return new GuardarLogError(in);
        }

        public GuardarLogError[] newArray(int size) {
            return (new GuardarLogError[size]);
        }
    };

    protected GuardarLogError(Parcel in) {
        this.respuestaTransaccion = (RespuestaTransaccion) in.readValue(RespuestaTransaccion.class.getClassLoader());
    }

    /**
     * No args constructor for use in serialization
     */
    public GuardarLogError() {
    }

    /**
     * @param respuestaTransaccion
     */
    public GuardarLogError(RespuestaTransaccion respuestaTransaccion) {
        super();
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("respuestaTransaccion", respuestaTransaccion).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}