
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SedeConvenio implements Parcelable {

    @SerializedName(value = "Codigo", alternate = {"codigo"})
    @Expose
    private String codigo;
    @SerializedName(value = "Nombre", alternate = {"nombre"})
    @Expose
    private String nombre;
    @SerializedName(value = "Area", alternate = {"area"})
    @Expose
    private Object area;
    @SerializedName(value = "Activo", alternate = {"activo"})
    @Expose
    private boolean activo;
    public final static Creator<SedeConvenio> CREATOR = new Creator<SedeConvenio>() {

        @SuppressWarnings({
                "unchecked"
        })
        public SedeConvenio createFromParcel(Parcel in) {
            return new SedeConvenio(in);
        }

        public SedeConvenio[] newArray(int size) {
            return (new SedeConvenio[size]);
        }
    };

    protected SedeConvenio(Parcel in) {
        this.codigo = ((String) in.readValue((String.class.getClassLoader())));
        this.nombre = ((String) in.readValue((String.class.getClassLoader())));
        this.area = ((Object) in.readValue((Object.class.getClassLoader())));
        this.activo = ((boolean) in.readValue((boolean.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public SedeConvenio() {
    }

    /**
     * @param nombre
     * @param codigo
     * @param area
     * @param activo
     */
    public SedeConvenio(String codigo, String nombre, Object area, boolean activo) {
        super();
        this.codigo = codigo;
        this.nombre = nombre;
        this.area = area;
        this.activo = activo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(codigo);
        dest.writeValue(nombre);
        dest.writeValue(area);
        dest.writeValue(activo);
    }

    public int describeContents() {
        return 0;
    }
}
