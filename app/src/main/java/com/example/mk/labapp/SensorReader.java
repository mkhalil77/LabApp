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
import android.widget.Button;
import android.widget.TextView;

public class SensorReader extends AppCompatActivity {
    public SensorData data = new SensorData();
    public SensorData CenterEar = new SensorData();
    public SensorData CenterHand = new SensorData();
    public SensorData CenterPocket = new SensorData();
    public SensorData CenterBackPocket = new SensorData();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_reader);


        final TextView CenterEarText = (TextView) findViewById(R.id.CenterEarText);
        final TextView CenterHandText = (TextView) findViewById(R.id.CenterHandText);
        final TextView CenterPocketText = (TextView) findViewById(R.id.CenterPocketText);
        final TextView CenterBackPocketText = (TextView) findViewById(R.id.CenterBackPocketText);



        Intent I = getIntent();

        if (I.hasExtra("CenterEar")) {
            CenterEar = (SensorData) I.getExtras().getParcelable("CenterEar");
            CenterHand = (SensorData) I.getExtras().getParcelable("CenterHand");
            CenterPocket = (SensorData) I.getExtras().getParcelable("CenterPocket");
            CenterBackPocket = (SensorData) I.getExtras().getParcelable("CenterBackPocket");


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
        SensorManager msensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        final Button Calibrate = (Button) findViewById(R.id.Calibration);

        //Calibration code
        Calibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SensorReader.this, CalibrationEars.class);

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