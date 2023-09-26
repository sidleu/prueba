
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ValidarRespuestaDemografica implements Parcelable {

    @SerializedName(value = "score", alternate = {"Score"})
    @Expose
    private String score;
    @SerializedName(value = "scoreService", alternate = {"ScoreService"})
    @Expose
    private String scoreService;
    @SerializedName(value = "esValido", alternate = {"EsValido"})
    @Expose
    private boolean esValido;
    @SerializedName(value = "respuestaTransaccion", alternate = {"RespuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Parcelable.Creator<ValidarRespuestaDemografica> CREATOR = new Creator<ValidarRespuestaDemografica>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ValidarRespuestaDemografica createFromParcel(Parcel in) {
            return new ValidarRespuestaDemografica(in);
        }

        public ValidarRespuestaDemografica[] newArray(int size) {
            return (new ValidarRespuestaDemografica[size]);
        }
    };

    protected ValidarRespuestaDemografica(Parcel in) {
        this.score = ((String) in.readValue((String.class.getClassLoader())));
        this.scoreService = ((String) in.readValue((String.class.getClassLoader())));
        this.esValido = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ValidarRespuestaDemografica() {
    }

    /**
     * @param respuestaTransaccion
     * @param score
     * @param esValido
     */
    public ValidarRespuestaDemografica(String score, String scoreService, boolean esValido, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.score = score;
        this.scoreService = scoreService;
        this.esValido = esValido;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreService() {
        return scoreService;
    }

    public void setScoreService(String scoreService) {
        this.scoreService = scoreService;
    }

    public boolean isEsValido() {
        return esValido;
    }

    public void setEsValido(boolean esValido) {
        this.esValido = esValido;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("score", score).append("scoreService", scoreService).append("esValido", esValido).append("respuestaTransaccion", respuestaTransaccion).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(score);
        dest.writeValue(scoreService);
        dest.writeValue(esValido);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
