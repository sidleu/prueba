
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class Data implements Parcelable
{

    @SerializedName("SourceImageFace")
    @Expose
    private SourceImageFace sourceImageFace;
    @SerializedName("FaceMatches")
    @Expose
    private List<FaceMatch> faceMatches = new ArrayList<FaceMatch>();
    @SerializedName("UnmatchedFaces")
    @Expose
    private List<UnmatchedFace> unmatchedFaces = new ArrayList<UnmatchedFace>();

    public final static Parcelable.Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
            ;

    protected Data(Parcel in) {
        this.sourceImageFace = ((SourceImageFace) in.readValue((SourceImageFace.class.getClassLoader())));
        in.readList(this.faceMatches, (FaceMatch.class.getClassLoader()));
        in.readList(this.unmatchedFaces, (UnmatchedFace.class.getClassLoader()));
    }

    public Data() {
    }

    public SourceImageFace getSourceImageFace() {
        return sourceImageFace;
    }

    public void setSourceImageFace(SourceImageFace sourceImageFace) {
        this.sourceImageFace = sourceImageFace;
    }

    public List<FaceMatch> getFaceMatches() {
        if (faceMatches == null) {
            return new ArrayList<>();
        }
        return faceMatches;
    }

    public void setFaceMatches(List<FaceMatch> faceMatches) {
        this.faceMatches = faceMatches;
    }

    public List<UnmatchedFace> getUnmatchedFaces() {
        if (unmatchedFaces == null) {
            return new ArrayList<>();
        }
        return unmatchedFaces;
    }

    public void setUnmatchedFaces(List<UnmatchedFace> unmatchedFaces) {
        this.unmatchedFaces = unmatchedFaces;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sourceImageFace", sourceImageFace).append("faceMatches", faceMatches).append("unmatchedFaces", unmatchedFaces).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sourceImageFace);
        dest.writeList(faceMatches);
        dest.writeList(unmatchedFaces);
    }

    public int describeContents() {
        return 0;
    }


}
