
package com.reconosersdk.reconosersdk.http.amazon.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Data implements Parcelable
{

    @SerializedName("ETag")
    @Expose
    private String eTag;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("Key")
    @Expose
    private String Key;
    @SerializedName("Bucket")
    @Expose
    private String bucket;
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
        this.eTag = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.key = ((String) in.readValue((String.class.getClassLoader())));
        this.Key = ((String) in.readValue((String.class.getClassLoader())));
        this.bucket = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getETag() {
        return eTag;
    }

    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKey1() {
        return key;
    }

    public void setKey1(String key1) {
        this.key = key1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("eTag", eTag).append("location", location).append("key", key).append("Key", Key).append("bucket", bucket).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(eTag);
        dest.writeValue(location);
        dest.writeValue(key);
        dest.writeValue(Key);
        dest.writeValue(bucket);
    }

    public int describeContents() {
        return  0;
    }

}
