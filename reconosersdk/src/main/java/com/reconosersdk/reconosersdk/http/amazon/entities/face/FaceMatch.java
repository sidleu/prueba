
package com.reconosersdk.reconosersdk.http.amazon.entities.face;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class FaceMatch implements Parcelable
{

    @SerializedName("Similarity")
    @Expose
    private Double similarity;
    @SerializedName("Face")
    @Expose
    private Face face;
    public final static Parcelable.Creator<FaceMatch> CREATOR = new Creator<FaceMatch>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FaceMatch createFromParcel(Parcel in) {
            return new FaceMatch(in);
        }

        public FaceMatch[] newArray(int size) {
            return (new FaceMatch[size]);
        }

    }
            ;

    protected FaceMatch(Parcel in) {
        this.similarity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.face = ((Face) in.readValue((Face.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public FaceMatch() {
    }

    /**
     *
     * @param face
     * @param similarity
     */
    public FaceMatch(Double similarity, Face face) {
        super();
        this.similarity = similarity;
        this.face = face;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("similarity", similarity).append("face", face).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(similarity);
        dest.writeValue(face);
    }

    public int describeContents() {
        return 0;
    }

}