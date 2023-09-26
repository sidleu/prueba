package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.reconosersdk.reconosersdk.utils.ConstantsOlimpia.LOOP_INIT_GET_TOKEN;

public class HeaderToken implements Parcelable {

    @SerializedName(value = "accessToken", alternate = {"AccessToken"})
    @Expose
    private String accessToken="";
    @SerializedName(value = "identityToken", alternate = {"IdentityToken"})
    @Expose
    private String identityToken="";
    @SerializedName(value = "tokenType", alternate = {"TokenType"})
    @Expose
    private String tokenType="";
    @SerializedName(value = "refreshToken", alternate = {"RefreshToken"})
    @Expose
    private String refreshToken="";
    @SerializedName(value = "errorDescription", alternate = {"ErrorDescription"})
    @Expose
    private String errorDescription="";
    @SerializedName(value = "expiresIn", alternate = {"ExpiresIn"})
    @Expose
    private int expiresIn=LOOP_INIT_GET_TOKEN;

    //Simple singleton
    private static HeaderToken instance;

    public static HeaderToken getInstance() {
        if (instance == null) {
            instance = new HeaderToken();
        }
        return instance;
    }

    public final static Parcelable.Creator<HeaderToken> CREATOR = new Creator<HeaderToken>() {
        @SuppressWarnings({
                "unchecked"
        })
        public HeaderToken createFromParcel(Parcel in) {
            return new HeaderToken(in);
        }

        public HeaderToken[] newArray(int size) {
            return (new HeaderToken[size]);
        }
    };

    protected HeaderToken(Parcel in) {
        this.accessToken = ((String) in.readValue((String.class.getClassLoader())));
        this.identityToken = ((String) in.readValue((String.class.getClassLoader())));
        this.tokenType = ((String) in.readValue((String.class.getClassLoader())));
        this.refreshToken = ((String) in.readValue((String.class.getClassLoader())));
        this.errorDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.expiresIn = ((int) in.readValue((int.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    private HeaderToken() {
    }

    /**
     * @param accessToken
     * @param identityToken
     * @param tokenType
     * @param refreshToken
     * @param errorDescription
     * @param expiresIn
     */
    public void setHeaderToken(String accessToken, String identityToken, String tokenType, String refreshToken, String errorDescription, int expiresIn) {
        this.accessToken = accessToken;
        this.identityToken = identityToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.errorDescription = errorDescription;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIdentityToken() {
        return identityToken;
    }

    public void setIdentityToken(String identityToken) {
        this.identityToken = identityToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(accessToken);
        dest.writeValue(identityToken);
        dest.writeValue(tokenType);
        dest.writeValue(refreshToken);
        dest.writeValue(errorDescription);
        dest.writeValue(expiresIn);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
