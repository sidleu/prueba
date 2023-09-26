package com.reconosersdk.reconosersdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataResult implements Parcelable {

    @SerializedName("textScan")
    private List<DataReadDocument> result = new ArrayList<>();

    public List<DataReadDocument> getResult() {
        return result;
    }

    public DataResult() {
    }

    public void setResult(List<DataReadDocument> result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.result);
    }

    protected DataResult(Parcel in) {
        this.result = in.createTypedArrayList(DataReadDocument.CREATOR);
    }

    public static final Creator<DataResult> CREATOR = new Creator<DataResult>() {
        @Override
        public DataResult createFromParcel(Parcel source) {
            return new DataResult(source);
        }

        @Override
        public DataResult[] newArray(int size) {
            return new DataResult[size];
        }
    };
}
