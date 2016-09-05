package com.example.mk.labapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class SensorReader extends AppCompatActivity  implements SensorEventListener {


    private SensorManager sensorManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_reader);


        sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);

        List<Sensor> list = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor sensor:list){


        }




    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(sensorEvent);
        }


        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            getAccelerometer(sensorEvent);
        }


        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            getAccelerometer(sensorEvent);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
