
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


public class UnmatchedFace implements Parcelable
{

    @SerializedName("BoundingBox")
    @Expose
    private BoundingBox_ boundingBox;
    @SerializedName("Confidence")
    @Expose
    private double confidence;
    @SerializedName("Landmarks")
    @Expose
    private List<Landmark> landmarks = new ArrayList<Landmark>();
    @SerializedName("Pose")
    @Expose
    private Pose pose;
    @SerializedName("Quality")
    @Expose
    private Quality quality;
    public final static Parcelable.Creator<UnmatchedFace> CREATOR = new Creator<UnmatchedFace>() {


        @SuppressWarnings({
            "unchecked"
        })
        public UnmatchedFace createFromParcel(Parcel in) {
            return new UnmatchedFace(in);
        }

        public UnmatchedFace[] newArray(int size) {
            return (new UnmatchedFace[size]);
        }

    }
    ;

    protected UnmatchedFace(Parcel in) {
        this.boundingBox = ((BoundingBox_) in.readValue((BoundingBox_.class.getClassLoader())));
        this.confidence = ((double) in.readValue((double.class.getClassLoader())));
        in.readList(this.landmarks, (Landmark.class.getClassLoader()));
        this.pose = ((Pose) in.readValue((Pose.class.getClassLoader())));
        this.quality = ((Quality) in.readValue((Quality.class.getClassLoader())));
    }

    public UnmatchedFace() {
    }

    public BoundingBox_ getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox_ boundingBox) {
        this.boundingBox = boundingBox;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public List<Landmark> getLandmarks() {
        if (landmarks == null) {
            return new ArrayList<>();
        }
        return landmarks;
    }

    public void setLandmarks(List<Landmark> landmarks) {
        this.landmarks = landmarks;
    }

    public Pose getPose() {
        return pose;
    }

    public void setPose(Pose pose) {
        this.pose = pose;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("boundingBox", boundingBox).append("confidence", confidence).append("landmarks", landmarks).append("pose", pose).append("quality", quality).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(boundingBox);
        dest.writeValue(confidence);
        dest.writeList(landmarks);
        dest.writeValue(pose);
        dest.writeValue(quality);
    }

    public int describeContents() {
        return  0;
    }

}
