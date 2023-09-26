package com.reconosersdk.reconosersdk.http.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TelephoneParamethers implements Parcelable {

    @SerializedName(value = "numberIMEI", alternate = {"NumberIMEI"})
    @Expose
    private String numberIMEI = "";
    @SerializedName(value = "currentIP", alternate = {"CurrentIP"})
    @Expose
    private String currentIP = "";

    //Simple singleton
    private static TelephoneParamethers instance;

    public static TelephoneParamethers getInstance() {
        if (instance == null) {
            instance = new TelephoneParamethers();
        }
        return instance;
    }

    public final static Creator<TelephoneParamethers> CREATOR = new Creator<TelephoneParamethers>() {
        @SuppressWarnings({
                "unchecked"
        })
        public TelephoneParamethers createFromParcel(Parcel in) {
            return new TelephoneParamethers(in);
        }

        public TelephoneParamethers[] newArray(int size) {
            return (new TelephoneParamethers[size]);
        }
    };

    protected TelephoneParamethers(Parcel in) {
        this.numberIMEI = ((String) in.readValue((String.class.getClassLoader())));
        this.currentIP = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    private TelephoneParamethers() {
    }

    public String getNumberIMEI() {
        return numberIMEI;
    }

    public void setNumberIMEI(String numberIMEI) {
        this.numberIMEI = numberIMEI;
    }

    public String getCurrentIP() {
        return currentIP;
    }

    public void setCurrentIP(String currentIP) {
        this.currentIP = currentIP;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(numberIMEI);
        dest.writeValue(currentIP);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
