package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutenticarAsesorOut implements Parcelable {

    @SerializedName(value = "Nombre", alternate = {"nombre"})
    @Expose
    private String nombre;
    @SerializedName(value = "Correo", alternate = {"correo"})
    @Expose
    private String correo;
    @SerializedName(value = "Telefono", alternate = {"telefono"})
    @Expose
    private String telefono;
    @SerializedName(value = "IdRol", alternate = {"idRol"})
    @Expose
    private int idRol;
    @SerializedName(value = "SedeCodigo", alternate = {"sedeCodigo"})
    @Expose
    private String sedeCodigo;
    @SerializedName(value = "SedeNombre", alternate = {"sedeNombre"})
    @Expose
    private String sedeNombre;
    @SerializedName(value = "Area", alternate = {"area"})
    @Expose
    private Object area;
    @SerializedName(value = "GuidConv", alternate = {"guidConv"})
    @Expose
    private String guidConv;
    @SerializedName(value = "NombreConv", alternate = {"nombreConv"})
    @Expose
    private String nombreConv;
    @SerializedName(value = "Activo", alternate = {"activo"})
    @Expose
    private boolean activo;
    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;
    public final static Parcelable.Creator<AutenticarAsesorOut> CREATOR = new Creator<AutenticarAsesorOut>() {

        @SuppressWarnings({
                "unchecked"
        })
        public AutenticarAsesorOut createFromParcel(Parcel in) {
            return new AutenticarAsesorOut(in);
        }

        public AutenticarAsesorOut[] newArray(int size) {
            return (new AutenticarAsesorOut[size]);
        }
    };

    protected AutenticarAsesorOut(Parcel in) {
        this.nombre = ((String) in.readValue((String.class.getClassLoader())));
        this.correo = ((String) in.readValue((String.class.getClassLoader())));
        this.telefono = ((String) in.readValue((String.class.getClassLoader())));
        this.idRol = ((int) in.readValue((int.class.getClassLoader())));
        this.sedeCodigo = ((String) in.readValue((String.class.getClassLoader())));
        this.sedeNombre = ((String) in.readValue((String.class.getClassLoader())));
        this.area = ((Object) in.readValue((Object.class.getClassLoader())));
        this.guidConv = ((String) in.readValue((String.class.getClassLoader())));
        this.nombreConv = ((String) in.readValue((String.class.getClassLoader())));
        this.activo = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.respuestaTransaccion = ((RespuestaTransaccion) in.readValue((RespuestaTransaccion.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public AutenticarAsesorOut() {
    }

    /**
     * @param nombre
     * @param sedeCodigo
     * @param respuestaTransaccion
     * @param area
     * @param nombreConv
     * @param telefono
     * @param idRol
     * @param activo
     * @param sedeNombre
     * @param correo
     * @param guidConv
     */
    public AutenticarAsesorOut(String nombre, String correo, String telefono, int idRol, String sedeCodigo, String sedeNombre, Object area, String guidConv, String nombreConv, boolean activo, RespuestaTransaccion respuestaTransaccion) {
        super();
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.idRol = idRol;
        this.sedeCodigo = sedeCodigo;
        this.sedeNombre = sedeNombre;
        this.area = area;
        this.guidConv = guidConv;
        this.nombreConv = nombreConv;
        this.activo = activo;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getSedeCodigo() {
        return sedeCodigo;
    }

    public void setSedeCodigo(String sedeCodigo) {
        this.sedeCodigo = sedeCodigo;
    }

    public String getSedeNombre() {
        return sedeNombre;
    }

    public void setSedeNombre(String sedeNombre) {
        this.sedeNombre = sedeNombre;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public String getGuidConv() {
        return guidConv;
    }

    public void setGuidConv(String guidConv) {
        this.guidConv = guidConv;
    }

    public String getNombreConv() {
        return nombreConv;
    }

    public void setNombreConv(String nombreConv) {
        this.nombreConv = nombreConv;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(nombre);
        dest.writeValue(correo);
        dest.writeValue(telefono);
        dest.writeValue(idRol);
        dest.writeValue(sedeCodigo);
        dest.writeValue(sedeNombre);
        dest.writeValue(area);
        dest.writeValue(guidConv);
        dest.writeValue(nombreConv);
        dest.writeValue(activo);
        dest.writeValue(respuestaTransaccion);
    }

    public int describeContents() {
        return 0;
    }
}
