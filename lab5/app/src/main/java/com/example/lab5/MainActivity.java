package com.example.lab5;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView degreeText;
    private ImageView compas;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor, magnometerSensor;
    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnometer = new float[3];
    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];
    private boolean isLastAccelerometerArrayCopied = false;
    private boolean isLastMagnetometerArrayCopied = false;
    private long lastUpdatedTime = 0;
    private float currentDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        degreeText = findViewById(R.id.degrees);
        compas = findViewById(R.id.compas);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //check if sensors available
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometerSensor);
        sensorManager.unregisterListener(this, magnometerSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometerSensor) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
            isLastAccelerometerArrayCopied = true;
        } else if (event.sensor == magnometerSensor){
            System.arraycopy(event.values, 0, lastMagnometer, 0, event.values.length);
            isLastMagnetometerArrayCopied = true;
        }
        if (isLastMagnetometerArrayCopied && isLastAccelerometerArrayCopied && System.currentTimeMillis() - lastUpdatedTime > 250){
            SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnometer);
            SensorManager.getOrientation(rotationMatrix, orientation);
            float azimuthInRadians = orientation[0];
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);

            RotateAnimation rotateAnimation =
                    new RotateAnimation(currentDegree, -azimuthInDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(250);
            rotateAnimation.setFillAfter(true);
            compas.startAnimation(rotateAnimation);
            currentDegree -= azimuthInDegrees;
            lastUpdatedTime = System.currentTimeMillis();
            int x = (int) azimuthInDegrees;
            degreeText.setText(x + "°");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}