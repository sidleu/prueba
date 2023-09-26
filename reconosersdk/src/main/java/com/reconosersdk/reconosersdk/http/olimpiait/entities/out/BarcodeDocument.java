package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarcodeDocument implements Parcelable {

    @SerializedName(value = "NumeroDocumento", alternate = {"numeroDocumento"})
    @Expose
    private String numeroDocumento;
    @SerializedName(value = "PrimerApellido", alternate = {"primerApellido"})
    @Expose
    private String primerApellido;
    @SerializedName(value = "SegundoApellido", alternate = {"segundoApellido"})
    @Expose
    private String segundoApellido;
    @SerializedName(value = "PrimerNombre", alternate = {"primerNombre"})
    @Expose
    private String primerNombre;
    @SerializedName(value = "SegundoNombre", alternate = {"segundoNombre"})
    @Expose
    private String segundoNombre;
    @SerializedName(value = "Sexo", alternate = {"sexo"})
    @Expose
    private String sexo;
    //Format YYYY-MMM-DD
    @SerializedName(value = "FechaNacimiento", alternate = {"fechaNacimiento"})
    @Expose
    private String fechaNacimiento;
    @SerializedName(value = "RH", alternate = {"rH"})
    @Expose
    private String rH;
    @SerializedName(value = "RespuestaTransaccion", alternate = {"respuestaTransaccion"})
    @Expose
    private RespuestaTransaccion respuestaTransaccion;

    public BarcodeDocument(String numeroDocumento, String primerApellido, String segundoApellido, String primerNombre, String segundoNombre, String sexo, String fechaNacimiento, String rH, RespuestaTransaccion respuestaTransaccion) {
        this.numeroDocumento = numeroDocumento;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.rH = rH;
        this.respuestaTransaccion = respuestaTransaccion;
    }

    public BarcodeDocument() {
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getrH() {
        return rH;
    }

    public void setrH(String rH) {
        this.rH = rH;
    }

    public RespuestaTransaccion getRespuestaTransaccion() {
        return respuestaTransaccion;
    }

    public void setRespuestaTransaccion(RespuestaTransaccion respuestaTransaccion) {
        this.respuestaTransaccion = respuestaTransaccion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.numeroDocumento);
        dest.writeString(this.primerApellido);
        dest.writeString(this.segundoApellido);
        dest.writeString(this.primerNombre);
        dest.writeString(this.segundoNombre);
        dest.writeString(this.sexo);
        dest.writeString(this.fechaNacimiento);
        dest.writeString(this.rH);
        dest.writeParcelable(this.respuestaTransaccion, flags);
    }

    protected BarcodeDocument(Parcel in) {
        this.numeroDocumento = in.readString();
        this.primerApellido = in.readString();
        this.segundoApellido = in.readString();
        this.primerNombre = in.readString();
        this.segundoNombre = in.readString();
        this.sexo = in.readString();
        this.fechaNacimiento = in.readString();
        this.rH = in.readString();
        this.respuestaTransaccion = in.readParcelable(RespuestaTransaccion.class.getClassLoader());
    }

    public static final Parcelable.Creator<BarcodeDocument> CREATOR = new Parcelable.Creator<BarcodeDocument>() {
        @Override
        public BarcodeDocument createFromParcel(Parcel source) {
            return new BarcodeDocument(source);
        }

        @Override
        public BarcodeDocument[] newArray(int size) {
            return new BarcodeDocument[size];
        }
    };
}
