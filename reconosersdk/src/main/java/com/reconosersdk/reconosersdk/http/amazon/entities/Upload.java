
package com.reconosersdk.reconosersdk.http.amazon.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Upload implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Parcelable.Creator<Upload> CREATOR = new Creator<Upload>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Upload createFromParcel(Parcel in) {
            return new Upload(in);
        }

        public Upload[] newArray(int size) {
            return (new Upload[size]);
        }

    }
    ;

    protected Upload(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public Upload() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("message", message).append("data", data).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
