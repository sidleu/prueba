package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MotivosCanceladoOut implements Parcelable {

    @SerializedName(value = "Motivos", alternate = {"motivos"})
    @Expose
    private List<String> motivos = new ArrayList<>();
    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Creator<MotivosCanceladoOut> CREATOR = new Creator<MotivosCanceladoOut>() {

        @SuppressWarnings({
                "unchecked"
        })
        public MotivosCanceladoOut createFromParcel(Parcel in) {
            return new MotivosCanceladoOut(in);
        }

        public MotivosCanceladoOut[] newArray(int size) {
            return (new MotivosCanceladoOut[size]);
        }
    };

    protected MotivosCanceladoOut(Parcel in) {
        in.readList(this.motivos, (String.class.getClassLoader()));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public MotivosCanceladoOut() {
    }

    /**
     * @param respuestaTransaccion
     * @param motivos
     */
    public MotivosCanceladoOut(List<String> motivos, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.motivos = motivos;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public List<String> getMotivos() {
        if (motivos == null) {
            return Collections.emptyList();
        }
        return motivos;
    }

    public void setMotivos(List<String> motivos) {
        this.motivos = motivos;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(motivos);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
