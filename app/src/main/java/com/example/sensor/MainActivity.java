package com.example.sensor;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txt_accel_x, txt_accel_y, txt_accel_z;
    TextView txt_gravity_x, txt_gravity_y, txt_gravity_z;
    TextView txt_linear_x, txt_linear_y, txt_linear_z;
    TextView txt_gyro_x, txt_gyro_y, txt_gyro_z;
    TextView txt_mag_x, txt_mag_y, txt_mag_z;
    TextView txt_ori_x, txt_ori_y, txt_ori_z;
    TextView textViewPathHint;
    TextView location_lat, location_long;
    TextView txt_prox,txt_time;

    ProgressBar prog_shakeMeter;

    //define sensor var
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGravity;
    private Sensor mLinear;
    private Sensor mGyroscope;
    private Sensor mMagnetic;
    private Sensor mOrientation;
    private Sensor mProximity;
    private LocationManager locationManager;

    private Button btnRecord, btnOpen;

    boolean flip = false;
    int flag=0;
    CSVWriter csvWriter = null;
    private String filename = null;
    String[] string = new String[15];

    float x,y,z;
//    private double accelerationCurrentValue;
//    private double accelerationPreviousValue;

//    private SensorEventListener sensorEventListener = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent sensorEvent) {
//            Sensor sensor = sensorEvent.sensor;
//
//            float x = sensorEvent.values[0];
//            float y = sensorEvent.values[1];
//            float z = sensorEvent.values[2];
//
////            accelerationCurrentValue = Math.sqrt( (x * x + y * y + z * z) );
////            accelerationPreviousValue = accelerationCurrentValue;
////            double changeInAccellration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
//
//
//
//            //update text views
////            txt_currentAccel.setText(("Current = " + accelerationCurrentValue));
////            txt_prevAccel.setText(("Prev = " + accelerationPreviousValue));
////            txt_acceleration.setText(("Acceleration Change = " + changeInAccellration));
//
//        }
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int i) {
//
//        }
//    };
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Sensor sensor = sensorEvent.sensor;
            if(sensor.getType() != Sensor.TYPE_PROXIMITY) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                    if (flip) {
//                    try {
//                        csvWriter = new CSVWriter(new FileWriter(filename, true));
//                        csvWriter.writeNext(new String[]{"Sensor 1: ",Float.toString(x),Float.toString(y),Float.toString(z)});
//                        csvWriter.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        textViewPathHint.setText(e.getMessage());
//                    }
                        string[0] = Float.toString(x);
                        string[1] = Float.toString(y);
                        string[2] = Float.toString(z);

                    }
//                tv.setText(new DecimalFormat("##.##").format(i2));

//                txt_gravity_x.setText(("x = " + new DecimalFormat("##.##").format(x)));
//                txt_gravity_y.setText(("z = " + new DecimalFormat("##.##").format(y)));
//                txt_gravity_z.setText(("y = " + new DecimalFormat("##.##").format(z)));
                    txt_gravity_x.setText(("x = " + x));
                    txt_gravity_y.setText(("y = " + y));
                    txt_gravity_z.setText(("z = " + z));
                } else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                    if (flip) {

                        string[3] = Float.toString(x);
                        string[4] = Float.toString(y);
                        string[5] = Float.toString(z);
                    }
                    txt_linear_x.setText(("x = " + x));
                    txt_linear_y.setText(("y = " + y));
                    txt_linear_z.setText(("z = " + z));
                } else if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    txt_accel_x.setText(("x = " + x));
                    txt_accel_y.setText(("y = " + y));
                    txt_accel_z.setText(("z = " + z));
                    if (flip) {
                        string[6] = Float.toString(x);
                        string[7] = Float.toString(y);
                        string[8] = Float.toString(z);
                    }
                } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    txt_gyro_x.setText(("x = " + x));
                    txt_gyro_y.setText(("y = " + y));
                    txt_gyro_z.setText(("z = " + z));
                    if (flip) {
                        string[9] = Float.toString(x);
                        string[10] = Float.toString(y);
                        string[11] = Float.toString(z);
                    }
                }else if (sensor.getType() == Sensor.TYPE_ORIENTATION) {
                    txt_ori_x.setText(("x = " + x));
                    txt_ori_y.setText(("y = " + y));
                    txt_ori_z.setText(("z = " + z));
                    if (flip) {
                        string[9] = Float.toString(x);
                        string[10] = Float.toString(y);
                        string[11] = Float.toString(z);
                    }
                } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    txt_mag_x.setText(("x = " + x));
                    txt_mag_y.setText(("y = " + y));
                    txt_mag_z.setText(("z = " + z));
                    if (flip) {
                        string[12] = Float.toString(x);
                        string[13] = Float.toString(y);
                        string[14] = Float.toString(z);
                        try {
                            csvWriter = new CSVWriter(new FileWriter(filename, true));
                            csvWriter.writeNext(string);
//                        csvWriter.writerow(new String[]{"Sensor 2: ",Float.toString(x),Float.toString(y),Float.toString(z)});
                            csvWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            textViewPathHint.setText(e.getMessage());
                        }
                    }
                }
//                else if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
//                String prop_x = sensorEvent.values[0];
//                txt_prox.setText((int) x);
//                }
            }else{
//                    else if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    x = sensorEvent.values[0];
                    txt_prox.setText(("x = " + x));

                }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_accel_x = findViewById(R.id.txt_accel_x);
        txt_accel_y = findViewById(R.id.txt_accel_y);
        txt_accel_z = findViewById(R.id.txt_accel_z);

        txt_gravity_x = findViewById(R.id.txt_gravity_x);
        txt_gravity_y = findViewById(R.id.txt_gravity_y);
        txt_gravity_z = findViewById(R.id.txt_gravity_z);

        txt_linear_x = findViewById(R.id.txt_linear_x);
        txt_linear_y = findViewById(R.id.txt_linear_y);
        txt_linear_z = findViewById(R.id.txt_linear_z);

        txt_gyro_x = findViewById(R.id.txt_gyro_x);
        txt_gyro_y = findViewById(R.id.txt_gyro_y);
        txt_gyro_z = findViewById(R.id.txt_gyro_z);

        txt_mag_x = findViewById(R.id.txt_mag_x);
        txt_mag_y = findViewById(R.id.txt_mag_y);
        txt_mag_z = findViewById(R.id.txt_mag_z);

        txt_ori_x = findViewById(R.id.txt_ori_x);
        txt_ori_y = findViewById(R.id.txt_ori_y);
        txt_ori_z = findViewById(R.id.txt_ori_z);

        location_lat = findViewById(R.id.location_lat);
        location_long = findViewById(R.id.location_long);

        txt_prox = findViewById(R.id.txt_prox);
        txt_time = findViewById(R.id.txt_time);

//        TextView textView = (TextView) findViewById(R.id.DATE);
//        txt_time.setText(DateUtils.formatDateTime(MainActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE));
//                , DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_12HOUR));

        textViewPathHint = findViewById(R.id.textViewPathHint);

        prog_shakeMeter = findViewById(R.id.prog_shakeMeter); //dont need it

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mLinear = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//        mOrientation = mSensorManager.getOrientation(rotationMatrix, orientationAngles);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        setDate(txt_time);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
//            return;
        }

        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

        btnRecord = (Button) findViewById(R.id.btnRecord);
        btnOpen = (Button) findViewById(R.id.btnOpen);

        if ((checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)   == PackageManager.PERMISSION_GRANTED)&& Build.VERSION.SDK_INT >= 23 ) {
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 0);

        }

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip = !flip;
                if(flip) {
                    filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" + "record.csv";
//                    filename = "/sdcard/hello.csv";
                    Toast.makeText(MainActivity.this, "Recording ...", Toast.LENGTH_SHORT).show();
//                    try {
//                        csvWriter = new CSVWriter(new FileWriter(filename, true));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        textViewPathHint.setText(e.getMessage());
//
//                    }

                }
                else {
//                    flag = -1;
                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    textViewPathHint.setText(filename);

                }
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                filename = "/data/data/com.example.sensor/hello.csv";
                try {
                    File fileOpen = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" + "record.csv");
//                    filename =

//                    File fileOpen = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/100.pdf");
                    Intent target = new Intent(Intent.ACTION_VIEW);

                    Uri getfiletoread = Uri.fromFile(fileOpen);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        getfiletoread = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", fileOpen);
                    }

                    target.setDataAndType(getfiletoread,"*/*");
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
// github
                    // hello world
                    Intent intent = Intent.createChooser(target, "Open File");

                    startActivity(intent);

                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
//                    System.out.println(fileOpen);
                    textViewPathHint.setText(e.getMessage());
                }
                //harshank
            }
        });
    }
    public void setDate (TextView view) {
        String str = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        txt_time.setText(strDate); }

    //    @Override
    public void onLocationChanged(Location location){
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        location_lat.setText("Latitude : " + latitude);
        location_long.setText("Latitude : " + longitude);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mLinear, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mProximity, SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(sensorEventListener, mMagnetic, SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

}