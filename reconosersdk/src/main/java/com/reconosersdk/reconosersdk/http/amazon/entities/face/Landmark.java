
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Landmark implements Parcelable
{

    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("X")
    @Expose
    private double x;
    @SerializedName("Y")
    @Expose
    private double y;
    public final static Parcelable.Creator<Landmark> CREATOR = new Creator<Landmark>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Landmark createFromParcel(Parcel in) {
            return new Landmark(in);
        }

        public Landmark[] newArray(int size) {
            return (new Landmark[size]);
        }

    }
    ;

    protected Landmark(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.x = ((double) in.readValue((double.class.getClassLoader())));
        this.y = ((double) in.readValue((double.class.getClassLoader())));
    }

    public Landmark() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("x", x).append("y", y).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(x);
        dest.writeValue(y);
    }

    public int describeContents() {
        return  0;
    }

}
