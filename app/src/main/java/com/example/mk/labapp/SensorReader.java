package com.example.mk.labapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class SensorReader extends AppCompatActivity {


    private SensorManager sensorManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_reader);

        final TextView DataFromAccelorometer = (TextView) findViewById(R.id.DataFromAcce);
        final TextView DataFromBarometer = (TextView) findViewById(R.id.DataFromBaro);
        final TextView DataFromGyro = (TextView) findViewById(R.id.DataFromGyro);
        final TextView DataFromMagne = (TextView) findViewById(R.id.DataFromMagne);
        final TextView DataFromLight = (TextView) findViewById(R.id.DataFromLight);
        final TextView DataFromProx = (TextView) findViewById(R.id.DataFromprox);

        SensorManager msensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);


        SensorEventListener sensorListner = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    DataFromAccelorometer.setText("" + Float.toString(sensorEvent.values[0]));
                    Log.d("TAG1", "onSensorChanged:Prox " + Float.toString(sensorEvent.values[0]));
                }


                if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    DataFromProx.setText("" + Float.toString(sensorEvent.values[0]));
                    Log.d("TAG1", "onSensorChanged:Prox ");
                }


                if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    DataFromMagne.setText("" + Float.toString(sensorEvent.values[0]));
                    Log.d("onSensorChanged:mag", Float.toString(sensorEvent.values[0]));
                }


                if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    DataFromGyro.setText("" + (Float.toString(sensorEvent.values[0])));
                    Log.d("onSensorChanged:Gyro", Float.toString(sensorEvent.values[0]));
                }


                if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
                    DataFromLight.setText("" + (Float.toString(sensorEvent.values[0])));
                    Log.d("TAG1", "onSensorChanged:Light " + Float.toString(sensorEvent.values[0]));
                }


                if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    String Pressure = Float.toString(sensorEvent.values[0]);
                    DataFromBarometer.setText("" + Pressure);
                    Log.d("onSensorChanged:Baro", Float.toString(sensorEvent.values[0]));
                }


            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_GAME);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_GAME);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_GAME);


/*
        List<Sensor> list = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Sensor Accelo = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);*/
    }


}