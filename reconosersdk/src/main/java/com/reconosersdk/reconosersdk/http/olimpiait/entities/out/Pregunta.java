
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pregunta implements Parcelable {

    @SerializedName(value = "IdPregunta", alternate = {"idPregunta"})
    @Expose
    private String idPregunta;
    @SerializedName(value = "TextoPregunta", alternate = {"textoPregunta"})
    @Expose
    private String textoPregunta;
    @SerializedName(value = "OpcionesRespuestas", alternate = {"opcionesRespuestas"})
    @Expose
    private List<OpcionesRespuesta> opcionesRespuestas = new ArrayList<>();
    public final static Parcelable.Creator<Pregunta> CREATOR = new Creator<Pregunta>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Pregunta createFromParcel(Parcel in) {
            return new Pregunta(in);
        }

        public Pregunta[] newArray(int size) {
            return (new Pregunta[size]);
        }
    };

    protected Pregunta(Parcel in) {
        this.idPregunta = ((String) in.readValue((String.class.getClassLoader())));
        this.textoPregunta = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.opcionesRespuestas, (com.reconosersdk.reconosersdk.http.olimpiait.entities.out.OpcionesRespuesta.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public Pregunta() {
    }

    /**
     * @param idPregunta
     * @param textoPregunta
     * @param opcionesRespuestas
     */
    public Pregunta(String idPregunta, String textoPregunta, List<OpcionesRespuesta> opcionesRespuestas) {
        super();
        this.idPregunta = idPregunta;
        this.textoPregunta = textoPregunta;
        this.opcionesRespuestas = opcionesRespuestas;
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    public List<OpcionesRespuesta> getOpcionesRespuestas() {
        if (opcionesRespuestas == null) {
            return Collections.emptyList();
        }
        return opcionesRespuestas;
    }

    public void setOpcionesRespuestas(List<OpcionesRespuesta> opcionesRespuestas) {
        this.opcionesRespuestas = opcionesRespuestas;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("idPregunta", idPregunta).append("textoPregunta", textoPregunta).append("opcionesRespuestas", opcionesRespuestas).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idPregunta);
        dest.writeValue(textoPregunta);
        dest.writeList(opcionesRespuestas);
    }

    public int describeContents() {
        return 0;
    }
}
