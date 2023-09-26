
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BoundingBox_ implements Parcelable
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
    public final static Parcelable.Creator<BoundingBox_> CREATOR = new Creator<BoundingBox_>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BoundingBox_ createFromParcel(Parcel in) {
            return new BoundingBox_(in);
        }

        public BoundingBox_[] newArray(int size) {
            return (new BoundingBox_[size]);
        }

    }
    ;

    protected BoundingBox_(Parcel in) {
        this.width = ((double) in.readValue((double.class.getClassLoader())));
        this.height = ((double) in.readValue((double.class.getClassLoader())));
        this.left = ((double) in.readValue((double.class.getClassLoader())));
        this.top = ((double) in.readValue((double.class.getClassLoader())));
    }

    public BoundingBox_() {
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
