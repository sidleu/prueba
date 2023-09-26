
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SedeConvenioOut implements Parcelable {

    @SerializedName(value = "SedeConvenio", alternate = {"sedeConvenio"})
    @Expose
    private List<SedeConvenio> sedeConvenio = new ArrayList<>();
    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Creator<SedeConvenioOut> CREATOR = new Creator<SedeConvenioOut>() {

        @SuppressWarnings({
                "unchecked"
        })
        public SedeConvenioOut createFromParcel(Parcel in) {
            return new SedeConvenioOut(in);
        }

        public SedeConvenioOut[] newArray(int size) {
            return (new SedeConvenioOut[size]);
        }
    };

    protected SedeConvenioOut(Parcel in) {
        in.readList(this.sedeConvenio, (SedeConvenio.class.getClassLoader()));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public SedeConvenioOut() {
    }

    /**
     * @param respuestaTransaccion
     * @param sedeConvenio
     */
    public SedeConvenioOut(List<SedeConvenio> sedeConvenio, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.sedeConvenio = sedeConvenio;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public List<SedeConvenio> getSedeConvenio() {
        if (sedeConvenio == null) {
            return Collections.emptyList();
        }
        return sedeConvenio;
    }

    public void setSedeConvenio(List<SedeConvenio> sedeConvenio) {
        this.sedeConvenio = sedeConvenio;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(sedeConvenio);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
