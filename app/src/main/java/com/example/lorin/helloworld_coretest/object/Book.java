package com.example.lorin.helloworld_coretest.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lorin on 16/1/25.
 */
public class Book implements Parcelable {


    private String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
