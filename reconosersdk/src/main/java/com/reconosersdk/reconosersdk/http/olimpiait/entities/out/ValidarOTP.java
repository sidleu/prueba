package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import kotlinx.android.parcel.RawValue;

public class ValidarOTP implements Parcelable {

    @SerializedName(value = "esValida", alternate = {"EsValida"})
    @Expose
    private boolean esValida = false;
    @SerializedName(value = "respuestaTransaccion", alternate = {"RespuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion = new RespuestaTransaccion();

    @SerializedName(value ="Data")
    private DataError dataError  = new DataError();
    public final static Parcelable.Creator<ValidarOTP> CREATOR = new Creator<ValidarOTP>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ValidarOTP createFromParcel(Parcel in) {
            return new ValidarOTP(in);
        }

        public ValidarOTP[] newArray(int size) {
            return (new ValidarOTP[size]);
        }
    };

    protected ValidarOTP(Parcel in) {
        this.esValida = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
        this.dataError = ((DataError) in.readValue((DataError.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ValidarOTP() {
    }

    /**
     * @param respuestaTransaccion
     * @param esValida
     * @param dataError
     */
    public ValidarOTP(boolean esValida, RespuestaTransaccion respuestaTransaccion, DataError dataError) {
        super();
        this.esValida = esValida;
        this.respuestaTransaccion = respuestaTransaccion;
        this.dataError = dataError;
    }

    public boolean isEsValida() {
        return esValida;
    }

    public void setEsValida(boolean esValida) {
        this.esValida = esValida;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public DataError getDataError() {
        return dataError;
    }

    public void setDataError(DataError dataError) {
        this.dataError = dataError;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("esValida", esValida).append("respuestaTransaccion", respuestaTransaccion)
                .append("dataError", dataError).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(esValida);
        dest.writeValue(respuestaTransaccion);
        dest.writeValue(dataError);
    }

    public int describeContents() {
        return 0;
    }
}
