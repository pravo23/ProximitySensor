package com.example.myapp.proximitysensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity  {

    private SensorManager proximityManager;
    private Sensor proximitysensor;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proximityManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        proximitysensor = proximityManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        image = (ImageView)findViewById(R.id.imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        proximityManager.registerListener(proximitySensorEventListener, proximitysensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        proximityManager.unregisterListener(proximitySensorEventListener);
    }

    SensorEventListener proximitySensorEventListener = new SensorEventListener() {

        long startTime = 0, differenceTime =0;
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.values[0] == 0) {     // near to screen
                image.setImageResource(R.drawable.undobar_button);
                startTime = System.currentTimeMillis();
                Log.d("time", Long.toString(startTime));
            }

            else {      // far from screen

                image.setImageResource(R.drawable.im);
                differenceTime = (System.currentTimeMillis() - startTime)/1000;
                Log.d("time1", Long.toString(differenceTime));
                Log.d("time2", Long.toString(startTime));

                differenceTime = differenceTime%60;

                if (differenceTime >= 2) {
                    Toast.makeText(MainActivity.this, "Time duration " +Long.toString(differenceTime)+ " seconds", Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


}
