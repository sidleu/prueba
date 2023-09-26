package com.reconosersdk.reconosersdk.http.olimpiait.entities.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObtenerToken implements Parcelable {

    @SerializedName(value = "clientId", alternate = {"ClientId"})
    @Expose
    private String clientId;
    @SerializedName(value = "clientSecret", alternate = {"ClientSecret"})
    @Expose
    private String clientSecret;

    public final static Parcelable.Creator<ObtenerToken> CREATOR = new Creator<ObtenerToken>() {
        @SuppressWarnings({
                "unchecked"
        })
        public ObtenerToken createFromParcel(Parcel in) {
            return new ObtenerToken(in);
        }

        public ObtenerToken[] newArray(int size) {
            return (new ObtenerToken[size]);
        }
    };

    protected ObtenerToken(Parcel in) {
        this.clientId = ((String) in.readValue((String.class.getClassLoader())));
        this.clientSecret = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ObtenerToken() {
    }

    /**
     * @param clientId
     * @param clientSecret
     */
    public ObtenerToken(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
