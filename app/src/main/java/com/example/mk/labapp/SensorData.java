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
        acceleration_vector = new double[3];
        magnetic_vector = new double[3];

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

    public double[] getAcceleration_vector() {
        return acceleration_vector;
    }

    public void setAcceleration_vector(double[] acceleration_vector) {
        this.acceleration_vector = acceleration_vector;
    }

    public int getProximity() {
        return Proximity;
    }

    public void setProximity(int proximity) {
        Proximity = proximity;
    }

    public double[] getMagnetic_vector() {
        return magnetic_vector;
    }

    public void setMagnetic_vector(double[] magnetic_vector) {
        this.magnetic_vector = magnetic_vector;
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

    public double getPressure() {
        return Pressure;
    }

    public void setPressure(double pressure) {
        Pressure = pressure;
    }

    public double getAltitude() {
        return Altitude;
    }

    public void setAltitude(double altitude) {
        Altitude = altitude;
    }

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