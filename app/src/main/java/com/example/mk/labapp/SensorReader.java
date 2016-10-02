package com.example.mk.labapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SensorReader extends AppCompatActivity {
    public SensorData data = new SensorData();
    public SensorData[] Center = new SensorData[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_reader);
        Intent I = getIntent();
        if (I.getSerializableExtra("Center") != null)
            Center = (SensorData[]) I.getSerializableExtra("Center");
        else {
            for (int h = 0; h < 5; h++)
                Center[h] = new SensorData();
        }




        final TextView DataFromAccelorometer = (TextView) findViewById(R.id.DataFromAcce);
        final TextView DataFromBarometer = (TextView) findViewById(R.id.DataFromBaro);
        final TextView DataFromMagne = (TextView) findViewById(R.id.DataFromMagne);
        final TextView DataFromAlti = (TextView) findViewById(R.id.DataFromAlti);
        final TextView DataFromLight = (TextView) findViewById(R.id.DataFromLight);
        final TextView DataFromProx = (TextView) findViewById(R.id.DataFromprox);
        SensorManager msensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        final Button Calibrate = (Button) findViewById(R.id.Calibration);

        //Calibration code
        Calibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SensorReader.this, CalibrationEars.class);
                intent.putExtra("Center", Center);
                startActivity(intent);
            }
        });



        SensorEventListener sensorListner = new SensorEventListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onSensorChanged(SensorEvent event) {


                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    final float alpha = (float) 0.8;
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(4);
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

                    DataFromAccelorometer.setText("(" + df.format(gravity[0]) + "  , " + df.format(gravity[1]) + " , " + df.format(gravity[2]) + ")");
                }


                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] == 0)
                        DataFromProx.setText("near");
                    else
                        DataFromProx.setText("far");

                }


                if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(4);
                    DataFromMagne.setText("( " + df.format(event.values[0]) + " , " + df.format(event.values[1]) + " , " + df.format(event.values[2]) + ")");

                }


                if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                    DataFromLight.setText("" + (Float.toString(event.values[0])));

                }


                if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    String Pressure = Float.toString(event.values[0]);

                    double height;
                    height = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, event.values[0]);
                    DataFromBarometer.setText("The pressure Value : " + Pressure);
                    DataFromAlti.setText("The Altitude from Sea Level : " + height);

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