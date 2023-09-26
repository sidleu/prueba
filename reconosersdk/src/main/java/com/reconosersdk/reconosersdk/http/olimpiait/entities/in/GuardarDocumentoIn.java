package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuardarDocumentoIn implements Parcelable {

    @SerializedName(value = "TipoDocumento", alternate = {"tipoDocumento"})
    @Expose
    private String tipoDocumento;

    @SerializedName(value = "Barcode", alternate = {"barcode"})
    @Expose
    private String barcode;

    @SerializedName(value = "ConvenioGuid", alternate = {"convenioGuid"})
    @Expose
    private String convenioGuid;

    public GuardarDocumentoIn(String tipoDocumento, String barcode, String convenioGuid) {
        this.tipoDocumento = tipoDocumento;
        this.barcode = barcode;
        this.convenioGuid = convenioGuid;
    }

    public GuardarDocumentoIn() {
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getConvenioGuid() {
        return convenioGuid;
    }

    public void setConvenioGuid(String convenioGuid) {
        this.convenioGuid = convenioGuid;
    }

    protected GuardarDocumentoIn(Parcel in) {
        tipoDocumento = in.readString();
        barcode = in.readString();
        convenioGuid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tipoDocumento);
        dest.writeString(barcode);
        dest.writeString(convenioGuid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GuardarDocumentoIn> CREATOR = new Creator<GuardarDocumentoIn>() {
        @Override
        public GuardarDocumentoIn createFromParcel(Parcel in) {
            return new GuardarDocumentoIn(in);
        }

        @Override
        public GuardarDocumentoIn[] newArray(int size) {
            return new GuardarDocumentoIn[size];
        }
    };
}
