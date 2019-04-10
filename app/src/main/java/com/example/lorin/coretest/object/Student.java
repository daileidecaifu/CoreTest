package com.example.lorin.coretest.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chengyi on 15/11/5.
 */
public class Student implements Parcelable {


  private String name;
  private int age;
  private Book book;


  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }


  public Student() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }


  protected Student(Parcel in) {
    name = in.readString();
    age = in.readInt();
    book = in.readParcelable(Book.class.getClassLoader());
  }

  public static final Creator<Student> CREATOR = new Creator<Student>() {
    @Override
    public Student createFromParcel(Parcel in) {
      return new Student(in);
    }

    @Override
    public Student[] newArray(int size) {
      return new Student[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeInt(age);
    dest.writeParcelable(book, flags);
  }
}
