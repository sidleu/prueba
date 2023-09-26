
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class OpcionesRespuesta implements Parcelable {

    @SerializedName(value = "IdRespuesta", alternate = {"idRespuesta"})
    @Expose
    private String idRespuesta;
    @SerializedName(value = "TextoRespuesta", alternate = {"textoRespuesta"})
    @Expose
    private String textoRespuesta;
    public final static Parcelable.Creator<OpcionesRespuesta> CREATOR = new Creator<OpcionesRespuesta>() {

        @SuppressWarnings({
                "unchecked"
        })
        public OpcionesRespuesta createFromParcel(Parcel in) {
            return new OpcionesRespuesta(in);
        }

        public OpcionesRespuesta[] newArray(int size) {
            return (new OpcionesRespuesta[size]);
        }
    };

    protected OpcionesRespuesta(Parcel in) {
        this.idRespuesta = ((String) in.readValue((String.class.getClassLoader())));
        this.textoRespuesta = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public OpcionesRespuesta() {
    }

    /**
     * @param idRespuesta
     * @param textoRespuesta
     */
    public OpcionesRespuesta(String idRespuesta, String textoRespuesta) {
        super();
        this.idRespuesta = idRespuesta;
        this.textoRespuesta = textoRespuesta;
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getTextoRespuesta() {
        return textoRespuesta;
    }

    public void setTextoRespuesta(String textoRespuesta) {
        this.textoRespuesta = textoRespuesta;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("idRespuesta", idRespuesta).append("textoRespuesta", textoRespuesta).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(idRespuesta);
        dest.writeValue(textoRespuesta);
    }

    public int describeContents() {
        return 0;
    }
}
