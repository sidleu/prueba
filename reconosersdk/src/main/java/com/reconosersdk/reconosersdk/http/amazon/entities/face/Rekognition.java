
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Rekognition implements Parcelable
{

    @SerializedName("ok")
    @Expose
    private boolean ok;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Parcelable.Creator<Rekognition> CREATOR = new Creator<Rekognition>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Rekognition createFromParcel(Parcel in) {
            return new Rekognition(in);
        }

        public Rekognition[] newArray(int size) {
            return (new Rekognition[size]);
        }

    };

    protected Rekognition(Parcel in) {
        this.ok = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public Rekognition() {
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("ok", ok).append("data", data).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ok);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
