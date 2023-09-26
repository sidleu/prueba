
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ErrorEntransaccion implements Parcelable {

    @SerializedName(value = "Codigo", alternate = {"codigo"})
    @Expose
    private String codigo;
    @SerializedName(value = "Descripcion", alternate = {"descripcion"})
    @Expose
    private String descripcion;
    public final static Parcelable.Creator<ErrorEntransaccion> CREATOR = new Creator<ErrorEntransaccion>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ErrorEntransaccion createFromParcel(Parcel in) {
            return new ErrorEntransaccion(in);
        }

        public ErrorEntransaccion[] newArray(int size) {
            return (new ErrorEntransaccion[size]);
        }
    };

    protected ErrorEntransaccion(Parcel in) {
        this.codigo = ((String) in.readValue((String.class.getClassLoader())));
        this.descripcion = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ErrorEntransaccion() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("codigo", codigo).append("descripcion", descripcion).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(codigo);
        dest.writeValue(descripcion);
    }

    public int describeContents() {
        return 0;
    }
}
