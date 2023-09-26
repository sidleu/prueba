
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosProceso implements Parcelable {

    @SerializedName(value = "GuidConv", alternate = {"guidConv"})
    @Expose
    private String guidConv;
    @SerializedName(value = "ProcesoConvenioGuid", alternate = {"procesoConvenioGuid"})
    @Expose
    private String procesoConvenioGuid;
    @SerializedName(value = "GuidCiu", alternate = {"guidCiu"})
    @Expose
    private String guidCiu;
    @SerializedName(value = "PrimerNombre", alternate = {"primerNombre"})
    @Expose
    private String primerNombre;
    @SerializedName(value = "SegundoNombre", alternate = {"segundoNombre"})
    @Expose
    private String segundoNombre;
    @SerializedName(value = "PrimerApellido", alternate = {"primerApellido"})
    @Expose
    private String primerApellido;
    @SerializedName(value = "SegundoApellido", alternate = {"segundoApellido"})
    @Expose
    private String segundoApellido;
    @SerializedName(value = "InfCandidato", alternate = {"infCandidato"})
    @Expose
    private Object infCandidato;
    @SerializedName(value = "TipoDoc", alternate = {"tipoDoc"})
    @Expose
    private String tipoDoc;
    @SerializedName(value = "NumDoc", alternate = {"numDoc"})
    @Expose
    private String numDoc;
    @SerializedName(value = "Email", alternate = {"email"})
    @Expose
    private String email;
    @SerializedName(value = "Celular", alternate = {"celular"})
    @Expose
    private String celular;
    @SerializedName(value = "Asesor", alternate = {"asesor"})
    @Expose
    private String asesor;
    @SerializedName(value = "Sede", alternate = {"sede"})
    @Expose
    private String sede;
    @SerializedName(value = "NombreSede", alternate = {"nombreSede"})
    @Expose
    private String nombreSede;
    @SerializedName(value = "CodigoCliente", alternate = {"codigoCliente"})
    @Expose
    private String codigoCliente;
    @SerializedName(value = "EstadoProceso", alternate = {"estadoProceso"})
    @Expose
    private int estadoProceso;
    @SerializedName(value = "Finalizado", alternate = {"finalizado"})
    @Expose
    private boolean finalizado;
    @SerializedName(value = "FechaRegistro", alternate = {"fechaRegistro"})
    @Expose
    private String fechaRegistro;
    @SerializedName(value = "FechaFinalizacion", alternate = {"fechaFinalizacion"})
    @Expose
    private Object fechaFinalizacion;
    public final static Creator<DatosProceso> CREATOR = new Creator<DatosProceso>() {

        @SuppressWarnings({
                "unchecked"
        })
        public DatosProceso createFromParcel(Parcel in) {
            return new DatosProceso(in);
        }

        public DatosProceso[] newArray(int size) {
            return (new DatosProceso[size]);
        }
    };

    protected DatosProceso(Parcel in) {
        this.guidConv = ((String) in.readValue((String.class.getClassLoader())));
        this.procesoConvenioGuid = ((String) in.readValue((String.class.getClassLoader())));
        this.guidCiu = ((String) in.readValue((String.class.getClassLoader())));
        this.primerNombre = ((String) in.readValue((String.class.getClassLoader())));
        this.segundoNombre = ((String) in.readValue((String.class.getClassLoader())));
        this.primerApellido = ((String) in.readValue((String.class.getClassLoader())));
        this.segundoApellido = ((String) in.readValue((String.class.getClassLoader())));
        this.infCandidato = ((Object) in.readValue((Object.class.getClassLoader())));
        this.tipoDoc = ((String) in.readValue((String.class.getClassLoader())));
        this.numDoc = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.celular = ((String) in.readValue((String.class.getClassLoader())));
        this.asesor = ((String) in.readValue((String.class.getClassLoader())));
        this.sede = ((String) in.readValue((String.class.getClassLoader())));
        this.nombreSede = ((String) in.readValue((String.class.getClassLoader())));
        this.codigoCliente = ((String) in.readValue((String.class.getClassLoader())));
        this.estadoProceso = ((int) in.readValue((int.class.getClassLoader())));
        this.finalizado = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.fechaRegistro = ((String) in.readValue((String.class.getClassLoader())));
        this.fechaFinalizacion = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public DatosProceso() {
    }

    /**
     * @param finalizado
     * @param tipoDoc
     * @param nombreSede
     * @param asesor
     * @param sede
     * @param infCandidato
     * @param fechaFinalizacion
     * @param fechaRegistro
     * @param guidCiu
     * @param primerNombre
     * @param segundoApellido
     * @param codigoCliente
     * @param numDoc
     * @param primerApellido
     * @param email
     * @param estadoProceso
     * @param segundoNombre
     * @param procesoConvenioGuid
     * @param guidConv
     * @param celular
     */
    public DatosProceso(String guidConv, String procesoConvenioGuid, String guidCiu, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Object infCandidato, String tipoDoc, String numDoc, String email, String celular, String asesor, String sede, String nombreSede, String codigoCliente, int estadoProceso, boolean finalizado, String fechaRegistro, Object fechaFinalizacion) {
        super();
        this.guidConv = guidConv;
        this.procesoConvenioGuid = procesoConvenioGuid;
        this.guidCiu = guidCiu;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.infCandidato = infCandidato;
        this.tipoDoc = tipoDoc;
        this.numDoc = numDoc;
        this.email = email;
        this.celular = celular;
        this.asesor = asesor;
        this.sede = sede;
        this.nombreSede = nombreSede;
        this.codigoCliente = codigoCliente;
        this.estadoProceso = estadoProceso;
        this.finalizado = finalizado;
        this.fechaRegistro = fechaRegistro;
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getGuidConv() {
        return guidConv;
    }

    public void setGuidConv(String guidConv) {
        this.guidConv = guidConv;
    }

    public String getProcesoConvenioGuid() {
        return procesoConvenioGuid;
    }

    public void setProcesoConvenioGuid(String procesoConvenioGuid) {
        this.procesoConvenioGuid = procesoConvenioGuid;
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

    public Object getInfCandidato() {
        return infCandidato;
    }

    public void setInfCandidato(Object infCandidato) {
        this.infCandidato = infCandidato;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(int estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Object getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Object fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(guidConv);
        dest.writeValue(procesoConvenioGuid);
        dest.writeValue(guidCiu);
        dest.writeValue(primerNombre);
        dest.writeValue(segundoNombre);
        dest.writeValue(primerApellido);
        dest.writeValue(segundoApellido);
        dest.writeValue(infCandidato);
        dest.writeValue(tipoDoc);
        dest.writeValue(numDoc);
        dest.writeValue(email);
        dest.writeValue(celular);
        dest.writeValue(asesor);
        dest.writeValue(sede);
        dest.writeValue(nombreSede);
        dest.writeValue(codigoCliente);
        dest.writeValue(estadoProceso);
        dest.writeValue(finalizado);
        dest.writeValue(fechaRegistro);
        dest.writeValue(fechaFinalizacion);
    }

    public int describeContents() {
        return 0;
    }
}