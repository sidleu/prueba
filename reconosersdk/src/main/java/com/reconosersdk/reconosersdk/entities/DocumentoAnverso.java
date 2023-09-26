package com.reconosersdk.reconosersdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentoAnverso implements Parcelable {


    @SerializedName(value = "stringAnverso", alternate = {"StringAnverso"})
    @Expose
    private String stringAnverso = "";
    @SerializedName(value = "stringAnversoBarcode", alternate = {"StringAnversoBarcode"})
    @Expose
    private String stringAnversoBarcode = "";

    //Simple singleton
    private static DocumentoAnverso instance;
    public DocumentoAnverso() {
    }

    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new DocumentoAnverso();
        }
    }

    public static DocumentoAnverso getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    public void setDocumentoAnverso(String stringAnverso, String stringAnversoBarcode) {
        this.stringAnverso = stringAnverso;
        this.stringAnversoBarcode = stringAnversoBarcode;
    }

    public DocumentoAnverso(String stringAnverso, String stringAnversoBarcode) {
        this.stringAnverso = stringAnverso;
        this.stringAnversoBarcode = stringAnversoBarcode;
    }

    public String getstringAnverso() {
        return stringAnverso;
    }

    public void setstringAnverso(String stringAnverso) {
        this.stringAnverso = stringAnverso;
    }

    public String getStringAnversoBarcode() {
        return stringAnversoBarcode;
    }

    public void setStringAnversoBarcode(String stringAnversoBarcode) {
        this.stringAnversoBarcode = stringAnversoBarcode;
    }

    public static final Parcelable.Creator<DocumentoAnverso> CREATOR = new Creator<DocumentoAnverso>() {
        @Override
        public DocumentoAnverso createFromParcel(Parcel source) {
            return new DocumentoAnverso(source);
        }

        @Override
        public DocumentoAnverso[] newArray(int size) {
            return new DocumentoAnverso[size];
        }
    };

    protected DocumentoAnverso(Parcel in) {
        /*this.stringAnverso = ((String) in.readValue((String.class.getClassLoader())));
        this.stringAnversoBarcode = ((String) in.readValue((String.class.getClassLoader())));*/
        this.stringAnverso = in.readString();
        this.stringAnversoBarcode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stringAnverso);
        dest.writeString(this.stringAnversoBarcode);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
