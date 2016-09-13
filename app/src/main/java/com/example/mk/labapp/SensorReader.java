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
            public void onSensorChanged(SensorEvent event) {


                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    final float alpha = (float) 0.8;
                    double[] gravity = new double[3];
                    double[] linear_acceleration = new double[3];
                    // Isolate the force of gravity with the low-pass filter.
                    gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                    gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                    gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                    // Remove the gravity contribution with the high-pass filter.
                    linear_acceleration[0] = event.values[0] - gravity[0];
                    linear_acceleration[1] = event.values[1] - gravity[1];
                    linear_acceleration[2] = event.values[2] - gravity[2];

                    DataFromAccelorometer.setText("x" + gravity[0] + "y" + gravity[1] + " z " + gravity[2]);
                }


                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] == 0)
                        DataFromProx.setText("near");
                    else
                        DataFromProx.setText("far");
                    Log.d("TAG1", "onSensorChanged:Prox ");
                }


                if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    DataFromMagne.setText("x" + Float.toString(event.values[0]) + "y" + Float.toString(event.values[0]) + "z" + Float.toString(event.values[0]));
                    Log.d("onSensorChanged:mag", Float.toString(event.values[0]));
                }


                if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                    DataFromLight.setText("" + (Float.toString(event.values[0])));
                    Log.d("TAG1", "onSensorChanged:Light " + Float.toString(event.values[0]));
                }


                if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    String Pressure = Float.toString(event.values[0]);
                    DataFromBarometer.setText("" + Pressure);
                    Log.d("onSensorChanged:Baro", Float.toString(event.values[0]));
                }


            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);


    }


}