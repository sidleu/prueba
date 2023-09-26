
package com.reconosersdk.reconosersdk.http.olimpiait.entities.out;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Value implements Parcelable {

    @SerializedName(value = "Date", alternate = {"date"})
    @Expose
    private String date;
    @SerializedName(value = "DataY", alternate = {"dataY"})
    @Expose
    private List<Integer> dataY = new ArrayList<>();
    public final static Creator<Value> CREATOR = new Creator<Value>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Value createFromParcel(Parcel in) {
            return new Value(in);
        }

        public Value[] newArray(int size) {
            return (new Value[size]);
        }
    };

    protected Value(Parcel in) {
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.dataY, (Integer.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public Value() {
    }

    /**
     * @param date
     * @param dataY
     */
    public Value(String date, List<Integer> dataY) {
        super();
        this.date = date;
        this.dataY = dataY;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getDataY() {
        if (dataY == null) {
            return Collections.emptyList();
        }
        return dataY;
    }

    public void setDataY(List<Integer> dataY) {
        this.dataY = dataY;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(date);
        dest.writeList(dataY);
    }

    public int describeContents() {
        return 0;
    }
}
