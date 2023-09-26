
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuardarCiudadano implements Parcelable {

    @SerializedName(value = "guidCiu", alternate = {"GuidCiu"})
    @Expose
    private String guidCiu;
    @SerializedName(value = "primerNombre", alternate = {"PrimerNombre"})
    @Expose
    private String primerNombre;
    @SerializedName(value = "segundoNombre", alternate = {"SegundoNombre"})
    @Expose
    private String segundoNombre;
    @SerializedName(value = "primerApellido", alternate = {"PrimerApellido"})
    @Expose
    private String primerApellido;
    @SerializedName(value = "segundoApellido", alternate = {"SegundoApellido"})
    @Expose
    private String segundoApellido;
    @SerializedName(value = "vivo", alternate = {"Vivo"})
    @Expose
    private boolean vivo;
    @SerializedName(value = "estadoDoc", alternate = {"EstadoDoc"})
    @Expose
    private String estadoDoc;
    @SerializedName(value = "fechaExp", alternate = {"FechaExp"})
    @Expose
    private String fechaExp;
    @SerializedName(value = "fechaNac", alternate = {"FechaNac"})
    @Expose
    private String fechaNac;
    @SerializedName(value = "localizacion", alternate = {"Localizacion"})
    @Expose
    private String localizacion;
    @SerializedName(value = "respuestaTransaccion", alternate = {"RespuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Parcelable.Creator<GuardarCiudadano> CREATOR = new Creator<GuardarCiudadano>() {

        @SuppressWarnings({
                "unchecked"
        })
        public GuardarCiudadano createFromParcel(Parcel in) {
            return new GuardarCiudadano(in);
        }

        public GuardarCiudadano[] newArray(int size) {
            return (new GuardarCiudadano[size]);
        }
    };

    protected GuardarCiudadano(Parcel in) {
        this.guidCiu = ((String) in.readValue((String.class.getClassLoader())));
        this.primerNombre = ((String) in.readValue((String.class.getClassLoader())));
        this.segundoNombre = ((String) in.readValue((String.class.getClassLoader())));
        this.primerApellido = ((String) in.readValue((String.class.getClassLoader())));
        this.segundoApellido = ((String) in.readValue((String.class.getClassLoader())));
        this.vivo = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.estadoDoc = ((String) in.readValue((String.class.getClassLoader())));
        this.fechaExp = ((String) in.readValue((String.class.getClassLoader())));
        this.fechaNac = ((String) in.readValue((String.class.getClassLoader())));
        this.localizacion = ((String) in.readValue((String.class.getClassLoader())));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public GuardarCiudadano() {
    }

    /**
     * @param fechaExp
     * @param respuestaTransaccion
     * @param fechaNac
     * @param primerApellido
     * @param localizacion
     * @param estadoDoc
     * @param segundoNombre
     * @param guidCiu
     * @param vivo
     * @param segundoApellido
     * @param primerNombre
     */
    public GuardarCiudadano(String guidCiu, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, boolean vivo, String estadoDoc, String fechaExp, String fechaNac, String localizacion, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.guidCiu = guidCiu;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.vivo = vivo;
        this.estadoDoc = estadoDoc;
        this.fechaExp = fechaExp;
        this.fechaNac = fechaNac;
        this.localizacion = localizacion;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public String getGuidCiu() {
        return guidCiu;
    }

    public void setGuidCiu(String guidCiu) {
        this.guidCiu = guidCiu;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public String getEstadoDoc() {
        return estadoDoc;
    }

    public void setEstadoDoc(String estadoDoc) {
        this.estadoDoc = estadoDoc;
    }

    public String getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(String fechaExp) {
        this.fechaExp = fechaExp;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(guidCiu);
        dest.writeValue(primerNombre);
        dest.writeValue(segundoNombre);
        dest.writeValue(primerApellido);
        dest.writeValue(segundoApellido);
        dest.writeValue(vivo);
        dest.writeValue(estadoDoc);
        dest.writeValue(fechaExp);
        dest.writeValue(fechaNac);
        dest.writeValue(localizacion);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}