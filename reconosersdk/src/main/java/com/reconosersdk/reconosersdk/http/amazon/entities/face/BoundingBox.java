
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BoundingBox implements Parcelable
{

    @SerializedName("Width")
    @Expose
    private double width;
    @SerializedName("Height")
    @Expose
    private double height;
    @SerializedName("Left")
    @Expose
    private double left;
    @SerializedName("Top")
    @Expose
    private double top;
    public final static Parcelable.Creator<BoundingBox> CREATOR = new Creator<BoundingBox>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BoundingBox createFromParcel(Parcel in) {
            return new BoundingBox(in);
        }

        public BoundingBox[] newArray(int size) {
            return (new BoundingBox[size]);
        }

    }
    ;

    protected BoundingBox(Parcel in) {
        this.width = ((double) in.readValue((double.class.getClassLoader())));
        this.height = ((double) in.readValue((double.class.getClassLoader())));
        this.left = ((double) in.readValue((double.class.getClassLoader())));
        this.top = ((double) in.readValue((double.class.getClassLoader())));
    }

    public BoundingBox() {
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("width", width).append("height", height).append("left", left).append("top", top).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(width);
        dest.writeValue(height);
        dest.writeValue(left);
        dest.writeValue(top);
    }

    public int describeContents() {
        return  0;
    }

}
