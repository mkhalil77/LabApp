package com.example.mk.labapp;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetectPosition extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_position);
        final TextView Position = (TextView) findViewById(R.id.Position);

        final TextView CenterEarText = (TextView) findViewById(R.id.CenterEarText);
        final TextView CenterHandText = (TextView) findViewById(R.id.CenterHandText);
        final TextView CenterPocketText = (TextView) findViewById(R.id.CenterPocketText);
        final TextView CenterBackPocketText = (TextView) findViewById(R.id.CenterBackPocketText);
        Button Detect = (Button) findViewById(R.id.Detect);


        //Get centers
        Intent I = new Intent();
        I = getIntent();


        SensorData CenterEar = new SensorData();
        SensorData CenterHand = new SensorData();
        SensorData CenterPocket = new SensorData();
        SensorData CenterBackPocket = new SensorData();

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


        }


        Detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Position.setVisibility(View.VISIBLE);
                Position.setText("Your Current Position is being detected !");


            }
        });


    }
}