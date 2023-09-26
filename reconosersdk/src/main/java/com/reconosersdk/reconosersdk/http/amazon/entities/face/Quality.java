
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Quality implements Parcelable
{

    @SerializedName("Brightness")
    @Expose
    private double brightness;
    @SerializedName("Sharpness")
    @Expose
    private double sharpness;
    public final static Parcelable.Creator<Quality> CREATOR = new Creator<Quality>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Quality createFromParcel(Parcel in) {
            return new Quality(in);
        }

        public Quality[] newArray(int size) {
            return (new Quality[size]);
        }

    }
    ;

    protected Quality(Parcel in) {
        this.brightness = ((double) in.readValue((double.class.getClassLoader())));
        this.sharpness = ((double) in.readValue((double.class.getClassLoader())));
    }

    public Quality() {
    }

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

    public double getSharpness() {
        return sharpness;
    }

    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("brightness", brightness).append("sharpness", sharpness).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(brightness);
        dest.writeValue(sharpness);
    }

    public int describeContents() {
        return  0;
    }

}
