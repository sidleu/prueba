
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Pose implements Parcelable
{

    @SerializedName("Roll")
    @Expose
    private double roll;
    @SerializedName("Yaw")
    @Expose
    private double yaw;
    @SerializedName("Pitch")
    @Expose
    private double pitch;
    public final static Parcelable.Creator<Pose> CREATOR = new Creator<Pose>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Pose createFromParcel(Parcel in) {
            return new Pose(in);
        }

        public Pose[] newArray(int size) {
            return (new Pose[size]);
        }

    }
    ;

    protected Pose(Parcel in) {
        this.roll = ((double) in.readValue((double.class.getClassLoader())));
        this.yaw = ((double) in.readValue((double.class.getClassLoader())));
        this.pitch = ((double) in.readValue((double.class.getClassLoader())));
    }

    public Pose() {
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("roll", roll).append("yaw", yaw).append("pitch", pitch).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(roll);
        dest.writeValue(yaw);
        dest.writeValue(pitch);
    }

    public int describeContents() {
        return  0;
    }

}
