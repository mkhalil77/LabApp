package com.example.mk.labapp;

/**
 * Created by Mk on 9/14/2016.
 */
public class SensorData {
    double[] acceleration_vector;
    Boolean Proximity;
    double[] magnetic_vector;
    double light;
    double Pressure;


    public SensorData() {
    }

    public SensorData(double[] acceleration_vector, Boolean proximity, double[] magnetic_vector, double light, double pressure) {
        this.acceleration_vector = acceleration_vector;
        Proximity = proximity;
        this.magnetic_vector = magnetic_vector;
        this.light = light;
        Pressure = pressure;
    }


    public double[] getAcceleration_vector() {
        return acceleration_vector;
    }

    public Boolean getProximity() {
        return Proximity;
    }

    public void setProximity(Boolean proximity) {
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

    public void setAcceleration_vector(double[] acceleration_vector) {
        this.acceleration_vector = acceleration_vector;

    }


}
