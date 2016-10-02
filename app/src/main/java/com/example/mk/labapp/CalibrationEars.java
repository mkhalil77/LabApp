package com.example.mk.labapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DecimalFormat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalibrationEars extends AppCompatActivity {

    SensorData[] Temp;
    SensorData Center;
    int a = 0;
    int b = 0;
    int c = 0, d = 0, e = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration_ears);
        Intent I = getIntent();
        Center = new SensorData();






        Temp = new SensorData[100];
        for (int j = 0; j < 100; j++)
            Temp[j] = new SensorData();



        a = 0;
        b = 0;
        c = 0;
        d = 0;
        e = 0;


        final TextView CenterEar = (TextView) findViewById(R.id.CenterEar);
        final TextView DataFromAccelorometer = (TextView) findViewById(R.id.DataFromAcce);
        final TextView DataFromBarometer = (TextView) findViewById(R.id.DataFromBaro);
        final TextView DataFromMagne = (TextView) findViewById(R.id.DataFromMagne);
        final TextView DataFromAlti = (TextView) findViewById(R.id.DataFromAlti);
        final TextView DataFromLight = (TextView) findViewById(R.id.DataFromLight);
        final TextView DataFromProx = (TextView) findViewById(R.id.DataFromprox);
        final Button Record = (Button) findViewById(R.id.record);
        final Button NextPhase = (Button) findViewById(R.id.nextPhase);
        final SensorManager msensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);


        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CenterEar.setText("Center Being Calcuated !");
                CenterEar.setVisibility(View.VISIBLE);
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

                            if (a < 100) {
                                Temp[a].acceleration_vector = new double[3];
                                Temp[a++].setAcceleration_vector(linear_acceleration);
                                Log.d("Value of A", "Value of A " + a);

                            }
                            DataFromAccelorometer.setText("(" + df.format(gravity[0]) + "  , " + df.format(gravity[1]) + " , " + df.format(gravity[2]) + ")");
                        }


                        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                            if (event.values[0] == 0) {
                                DataFromProx.setText("near");
                                for (b = 0; b < 100; b++)
                                    Temp[b].setProximity(1);
                            } else {
                                DataFromProx.setText("far");
                                for (b = 0; b < 100; b++)
                                    Temp[b].setProximity(1);

                                }
                            Log.d("Value of B", "Value of B " + b);

                        }



                        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                            DecimalFormat df = new DecimalFormat();
                            df.setMaximumFractionDigits(4);
                            DataFromMagne.setText("( " + df.format(event.values[0]) + " , " + df.format(event.values[1]) + " , " + df.format(event.values[2]) + ")");
                            if (c < 100) {
                                Temp[c].magnetic_vector = new double[3];
                                Temp[c].magnetic_vector[0] = event.values[0];
                                Temp[c].magnetic_vector[1] = event.values[1];
                                Temp[c++].magnetic_vector[2] = event.values[2];
                                Log.d("Value of C", "Value of C " + c);

                            }

                        }


                        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                            DataFromLight.setText("" + (Float.toString(event.values[0])));
                            Log.d("Value of d", "Value of D " + d);

                            if (d < 100)
                                for (int i = 0; i < 100; i++) {
                                    Temp[d].light = event.values[0];
                                    d++;
                                }
                            Log.d("Value of d", "Value of D " + d);
                        }


                        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                            String Pressure = Float.toString(event.values[0]);

                            double height;
                            height = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, event.values[0]);
                            DataFromBarometer.setText("The pressure Value : " + Pressure);
                            DataFromAlti.setText("The Altitude from Sea Level : " + height);

                            if (e < 100) {
                                Log.d("Value of E", "Value of E " + e);
                                Temp[e].Pressure = event.values[0];
                                Temp[e++].Altitude = height;
                                Log.d("Value of E", "Value of E " + e);
                            }
                        }


                        if ((a == 100) && (b == 100) && (c == 100) && (d == 100) && (e == 100)) {
                            SensorData avg = new SensorData();
                            avg.acceleration_vector = new double[3];
                            avg.magnetic_vector = new double[3];

                            for (int i = 0; i < 100; i++) {
                                avg.acceleration_vector[0] = avg.acceleration_vector[0] + Temp[i].acceleration_vector[0];
                                avg.acceleration_vector[1] = avg.acceleration_vector[1] + Temp[i].acceleration_vector[1];
                                avg.acceleration_vector[2] = avg.acceleration_vector[2] + Temp[i].acceleration_vector[2];
                                avg.Proximity = avg.Proximity + Temp[i].Proximity;
                                avg.magnetic_vector[0] = avg.magnetic_vector[0] + Temp[i].magnetic_vector[0];
                                avg.magnetic_vector[1] = avg.magnetic_vector[1] + Temp[i].magnetic_vector[1];
                                avg.magnetic_vector[2] = avg.magnetic_vector[2] + Temp[i].magnetic_vector[2];
                                avg.Altitude = avg.Altitude + Temp[i].Altitude;
                                avg.Pressure = avg.Pressure + Temp[i].Pressure;
                                avg.light = avg.light + Temp[i].light;


                            }

                            avg.acceleration_vector[0] = avg.acceleration_vector[0] / 100;
                            avg.acceleration_vector[1] = avg.acceleration_vector[1] / 100;
                            avg.acceleration_vector[2] = avg.acceleration_vector[2] / 100;
                            avg.Proximity = avg.Proximity / 100;
                            avg.magnetic_vector[0] = avg.magnetic_vector[0] / 100;
                            avg.magnetic_vector[1] = avg.magnetic_vector[1] / 100;
                            avg.magnetic_vector[2] = avg.magnetic_vector[2] / 100;
                            avg.Altitude = avg.Altitude / 100;
                            avg.Pressure = avg.Pressure / 100;
                            avg.light = avg.light / 100;
                            Log.d("Center", avg.acceleration_vector[0] + " " + avg.acceleration_vector[1] + " " + avg.acceleration_vector[2] + " "
                                    + avg.Proximity + " " + avg.Altitude + " " + avg.Pressure);

                            DecimalFormat df = new DecimalFormat();
                            df.setMaximumFractionDigits(2);


                            Center = avg;
                            CenterEar.setText("Center : Acceleration vector : (" + df.format(avg.acceleration_vector[0]) + " ," + df.format(avg.acceleration_vector[1]) + ", " + df.format(avg.acceleration_vector[2]) + ") "
                                    + "Ligh " + avg.light + "Magne Vector (" + df.format(avg.magnetic_vector[0]) + "," + df.format(avg.magnetic_vector[1]) + "," + df.format(avg.magnetic_vector[2]) + " )" + "Proximity " + avg.Proximity + " Altitude :  " + df.format(avg.Altitude) + " Pressure " + df.format(avg.Pressure));


                            try {
                                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                r.play();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            a++;

                        }






                    }


                    @Override
                    public void onAccuracyChanged(Sensor sensor, int i) {

                    }
                };

                msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
                msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_UI);
                msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI);
                msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_UI);
                msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_UI);


            }

        });





        NextPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenet = new Intent(CalibrationEars.this, CalibrationHand.class);
                {


                    intenet.putExtra("CenterEar", Center);
                    startActivity(intenet);
                }
            }
        })
        ;





    }


}


