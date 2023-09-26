
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolicitarPreguntasDemograficas implements Parcelable {

    @SerializedName(value = "IdCuestionario", alternate = {"idCuestionario"})
    @Expose
    private String idCuestionario;
    @SerializedName(value = "RegistroCuestionario", alternate = {"registroCuestionario"})
    @Expose
    private int registroCuestionario;
    @SerializedName(value = "Preguntas", alternate = {"preguntas"})
    @Expose
    private List<Pregunta> preguntas = new ArrayList<>();
    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Parcelable.Creator<SolicitarPreguntasDemograficas> CREATOR = new Creator<SolicitarPreguntasDemograficas>() {

        @SuppressWarnings({
                "unchecked"
        })
        public SolicitarPreguntasDemograficas createFromParcel(Parcel in) {
            return new SolicitarPreguntasDemograficas(in);
        }

        public SolicitarPreguntasDemograficas[] newArray(int size) {
            return (new SolicitarPreguntasDemograficas[size]);
        }
    };

    protected SolicitarPreguntasDemograficas(Parcel in) {
        this.idCuestionario = ((String) in.readValue((String.class.getClassLoader())));
        this.registroCuestionario = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.preguntas, (com.reconosersdk.reconosersdk.http.olimpiait.entities.out.Pregunta.class.getClassLoader()));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public SolicitarPreguntasDemograficas() {
    }

    /**
     * @param preguntas
     * @param respuestaTransaccion
     * @param idCuestionario
     * @param registroCuestionario
     */
    public SolicitarPreguntasDemograficas(String idCuestionario, int registroCuestionario, List<Pregunta> preguntas, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.idCuestionario = idCuestionario;
        this.registroCuestionario = registroCuestionario;
        this.preguntas = preguntas;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public String getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(String idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public int getRegistroCuestionario() {
        return registroCuestionario;
    }

    public void setRegistroCuestionario(int registroCuestionario) {
        this.registroCuestionario = registroCuestionario;
    }

    public List<Pregunta> getPreguntas() {
        if (preguntas == null) {
            return Collections.emptyList();
        }
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("idCuestionario", idCuestionario).append("registroCuestionario", registroCuestionario).append("preguntas", preguntas).append("respuestaTransaccion", respuestaTransaccion).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idCuestionario);
        dest.writeValue(registroCuestionario);
        dest.writeList(preguntas);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}