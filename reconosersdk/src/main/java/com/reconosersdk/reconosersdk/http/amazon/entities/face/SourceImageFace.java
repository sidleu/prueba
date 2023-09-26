
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SourceImageFace implements Parcelable
{

    @SerializedName("BoundingBox")
    @Expose
    private BoundingBox boundingBox;
    @SerializedName("Confidence")
    @Expose
    private double confidence;
    public final static Parcelable.Creator<SourceImageFace> CREATOR = new Creator<SourceImageFace>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SourceImageFace createFromParcel(Parcel in) {
            return new SourceImageFace(in);
        }

        public SourceImageFace[] newArray(int size) {
            return (new SourceImageFace[size]);
        }

    }
    ;

    protected SourceImageFace(Parcel in) {
        this.boundingBox = ((BoundingBox) in.readValue((BoundingBox.class.getClassLoader())));
        this.confidence = ((double) in.readValue((double.class.getClassLoader())));
    }

    public SourceImageFace() {
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("boundingBox", boundingBox).append("confidence", confidence).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(boundingBox);
        dest.writeValue(confidence);
    }

    public int describeContents() {
        return  0;
    }

}
