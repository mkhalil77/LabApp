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
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DetectPosition extends AppCompatActivity {


    public SensorData data = new SensorData();
    public SensorData CenterEar = new SensorData();
    public SensorData CenterHand = new SensorData();
    public SensorData CenterPocket = new SensorData();
    public SensorData CenterBackPocket = new SensorData();

    SensorData[] Temp;
    int a = 0;
    int b = 0;
    int c = 0, d = 0, e = 0;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_position);


        Temp = new SensorData[100];
        for (int j = 0; j < 100; j++)
            Temp[j] = new SensorData();


        a = 0;
        b = 0;
        c = 0;
        d = 0;
        e = 0;


        final TextView CenterEarText = (TextView) findViewById(R.id.CenterEarText);
        final TextView CenterHandText = (TextView) findViewById(R.id.CenterHandText);
        final TextView CenterPocketText = (TextView) findViewById(R.id.CenterPocketText);
        final TextView CenterBackPocketText = (TextView) findViewById(R.id.CenterBackPocketText);


        Intent I = getIntent();

        if (I.hasExtra("CenterEar")) {
            CenterEar = I.getExtras().getParcelable("CenterEar");
            CenterHand = I.getExtras().getParcelable("CenterHand");
            CenterPocket = I.getExtras().getParcelable("CenterPocket");
            CenterBackPocket = I.getExtras().getParcelable("CenterBackPocket");


            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);


            CenterEarText.setText("CenterEar : Acceleration vector : (" + df.format(CenterEar.acceleration_vector[0]) + " ," + df.format(CenterEar.acceleration_vector[1]) + ", " + df.format(CenterEar.acceleration_vector[2]) + ") "
                    + "Ligh " + CenterEar.light + "Magne Vector (" + df.format(CenterEar.magnetic_vector[0]) + "," + df.format(CenterEar.magnetic_vector[1]) + "," + df.format(CenterEar.magnetic_vector[2]) + " )" + "Proximity " + CenterEar.Proximity + " Altitude :  " + df.format(CenterEar.Altitude) + " Pressure " + df.format(CenterEar.Pressure));


            CenterHandText.setText("CenterHand : Acceleration vector : (" + df.format(CenterHand.acceleration_vector[0]) + " ," + df.format(CenterHand.acceleration_vector[1]) + ", " + df.format(CenterHand.acceleration_vector[2]) + ") "
                    + "Ligh " + CenterHand.light + "Magne Vector (" + df.format(CenterHand.magnetic_vector[0]) + "," + df.format(CenterHand.magnetic_vector[1]) + "," + df.format(CenterHand.magnetic_vector[2]) + " )" + "Proximity " + CenterHand.Proximity + " Altitude :  " + df.format(CenterHand.Altitude) + " Pressure " + df.format(CenterHand.Pressure));


            CenterPocketText.setText("CenterPocket : Acceleration vector : (" + df.format(CenterPocket.acceleration_vector[0]) + " ," + df.format(CenterPocket.acceleration_vector[1]) + ", " + df.format(CenterPocket.acceleration_vector[2]) + ") "
                    + "Ligh " + CenterPocket.light + "Magne Vector (" + df.format(CenterPocket.magnetic_vector[0]) + "," + df.format(CenterPocket.magnetic_vector[1]) + "," + df.format(CenterPocket.magnetic_vector[2]) + " )" + "Proximity " + CenterPocket.Proximity + " Altitude :  " + df.format(CenterPocket.Altitude) + " Pressure " + df.format(CenterPocket.Pressure));


            CenterBackPocketText.setText("CenterbackPocket : Acceleration vector : (" + df.format(CenterBackPocket.acceleration_vector[0]) + " ," + df.format(CenterBackPocket.acceleration_vector[1]) + ", " + df.format(CenterBackPocket.acceleration_vector[2]) + ") "
                    + "Ligh " + CenterBackPocket.light + "Magne Vector (" + df.format(CenterBackPocket.magnetic_vector[0]) + "," + df.format(CenterBackPocket.magnetic_vector[1]) + "," + df.format(CenterBackPocket.magnetic_vector[2]) + " )" + "Proximity " + CenterBackPocket.Proximity + " Altitude :  " + df.format(CenterBackPocket.Altitude) + " Pressure " + df.format(CenterBackPocket.Pressure));


            CenterHandText.setVisibility(View.VISIBLE);
            CenterEarText.setVisibility(View.VISIBLE);
            CenterPocketText.setVisibility(View.VISIBLE);
            CenterBackPocketText.setVisibility(View.VISIBLE);


        }


        final TextView DataFromAccelorometer = (TextView) findViewById(R.id.DataFromAcce);
        final TextView DataFromBarometer = (TextView) findViewById(R.id.DataFromBaro);
        final TextView DataFromMagne = (TextView) findViewById(R.id.DataFromMagne);
        final TextView DataFromAlti = (TextView) findViewById(R.id.DataFromAlti);
        final TextView DataFromLight = (TextView) findViewById(R.id.DataFromLight);
        final TextView DataFromProx = (TextView) findViewById(R.id.DataFromprox);
        final SensorManager msensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        final TextView CurrentCenter = (TextView) findViewById(R.id.CurrentCenter);
        final TextView Position = (TextView) findViewById(R.id.Position);


        final SensorEventListener sensorListner = new SensorEventListener() {
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

                    }
                    DataFromAccelorometer.setText("(" + df.format(gravity[0]) + "  , " + df.format(gravity[1]) + " , " + df.format(gravity[2]) + ")");
                }


                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] == 0) {
                        DataFromProx.setText("near");

                        for (b = 0; b < 100; b++)
                            Temp[b].setProximity(0);
                    } else {
                        

                        DataFromProx.setText("far");
                        for (b = 0; b < 100; b++)
                            Temp[b].setProximity(1);
                    }


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

                    }
                }


                if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                    DataFromLight.setText("" + (Float.toString(event.values[0])));


                    if (d < 100)
                        for (int i = 0; i < 100; i++) {
                            Temp[d].light = event.values[0];
                            d++;
                        }


                }


                if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    String Pressure = Float.toString(event.values[0]);

                    double height;
                    height = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, event.values[0]);
                    DataFromBarometer.setText("The pressure Value : " + Pressure);
                    DataFromAlti.setText("The Altitude from Sea Level : " + height);


                    if (e < 100) {

                        Temp[e].Pressure = event.values[0];
                        Temp[e++].Altitude = height;

                    }


                }

                //updating center

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

                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);

                    CurrentCenter.setText("Curent Position : Acceleration vector : (" + df.format(avg.acceleration_vector[0]) + " ," + df.format(avg.acceleration_vector[1]) + ", " + df.format(avg.acceleration_vector[2]) + ") "
                            + "Ligh " + avg.light + "Magne Vector (" + df.format(avg.magnetic_vector[0]) + "," + df.format(avg.magnetic_vector[1]) + "," + df.format(avg.magnetic_vector[2]) + " )" + "Proximity " + avg.Proximity + " Altitude :  " + df.format(avg.Altitude) + " Pressure " + df.format(avg.Pressure));

                    CurrentCenter.setVisibility(View.VISIBLE);


                    double Distance1 = Calculate_distance(avg, CenterEar);
                    double Distance2 = Calculate_distance(avg, CenterHand);
                    double Distance3 = Calculate_distance(avg, CenterPocket);
                    double Distance4 = Calculate_distance(avg, CenterBackPocket);


                    int closest = findmax(Distance1, Distance2, Distance3, Distance4);


                    if (closest == 1)
                        Position.setText("The phone is in your Ear");

                    if (closest == 2)
                        Position.setText("The phone is in your hand");

                    if (closest == 3)
                        Position.setText("The phone is in your Pocket");

                    if (closest == 4)
                        Position.setText("The phone is in your Back Pocket");


                    Position.setVisibility(View.VISIBLE);


                }
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }

            public int findmax(double a, double b, double c, double d) {
                if (a <= b && a <= c && a <= d)
                    return 1;
                if (b <= a && b <= c && b <= d)
                    return 2;
                if (c <= b && c <= a && c <= d)
                    return 3;
                if (d <= b && d <= c && d <= a)
                    return 4;

                return 5;

            }

            public double VectorDistance(double[] Vector1, double[] Vector2) {
                double distance;

                distance = Math.sqrt(Math.pow((Vector1[0] - Vector2[0]), 2) + Math.pow((Vector1[1] - Vector2[1]), 2) + Math.pow((Vector1[2] - Vector2[2]), 2));

                return distance;
            }

            public double Calculate_distance(SensorData Current, SensorData Center) {
                double Distance;
                Distance = Math.sqrt(Math.pow((Current.Altitude - Center.Altitude) * 10, 2) + Math.pow((Current.Pressure - Center.Pressure) * 10, 2) + Math.pow((Current.light - Center.light), 2) + Math.pow((Current.Proximity - Center.Proximity * 5), 2) + Math.pow(VectorDistance(Current.acceleration_vector, Center.acceleration_vector), 2) + Math.pow(VectorDistance(Current.magnetic_vector, Center.magnetic_vector), 2));


                return Distance;
            }
        };


        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        msensorManager.registerListener(sensorListner, msensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);


    }


}