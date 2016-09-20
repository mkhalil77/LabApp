package com.example.mk.labapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mk on 9/14/2016.
 */
public class SensorData implements Parcelable {
    double[] acceleration_vector;
    int Proximity;
    double[] magnetic_vector;
    double light;
    double Pressure;
    double Altitude;


    public SensorData() {
    }

    protected SensorData(Parcel in) {
        acceleration_vector = in.createDoubleArray();
        Proximity = in.readInt();
        magnetic_vector = in.createDoubleArray();
        light = in.readDouble();
        Pressure = in.readDouble();
        Altitude = in.readDouble();
    }

    public static final Creator<SensorData> CREATOR = new Creator<SensorData>() {
        @Override
        public SensorData createFromParcel(Parcel in) {
            return new SensorData(in);
        }

        @Override
        public SensorData[] newArray(int size) {
            return new SensorData[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDoubleArray(acceleration_vector);
        parcel.writeInt(Proximity);
        parcel.writeDoubleArray(magnetic_vector);
        parcel.writeDouble(light);
        parcel.writeDouble(Pressure);
        parcel.writeDouble(Altitude);
    }
}
