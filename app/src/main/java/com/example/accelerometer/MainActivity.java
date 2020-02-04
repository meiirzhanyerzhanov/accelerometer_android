package com.example.accelerometer;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

public class MainActivity extends Activity implements SensorEventListener {


    private Paint mPaint;
    private SensorManager m;
    private Sensor accel;
    private View v;
    public float vertex_x, vertex_y;
    public float screen_x, screen_y;
    public float initial_x, initial_y;
    public final float circle_rad = 50.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m = (SensorManager) this.getSystemService(SENSOR_SERVICE );
        accel = m.getDefaultSensor(Sensor.TYPE_ACCELEROMETER );
        m. registerListener( this,accel,SensorManager.SENSOR_DELAY_GAME);

        Display display = getWindowManager().getDefaultDisplay();
        screen_x = (float)display.getWidth() - 50;
        screen_y = (float)display.getHeight() - 50;

        vertex_x = screen_x / 2;
        vertex_y = screen_y / 2;
        initial_x = vertex_x;
        initial_y = vertex_y;

        v = new CustomDrawableView(this);
        setContentView(v);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            initial_x = initial_x - sensorEvent.values[0]*10;
            vertex_x =  initial_x;

            initial_y = initial_y + sensorEvent.values[1]*10;
            vertex_y = initial_y;

            if (vertex_x < 0)
                vertex_x = 0;
            if(vertex_x + circle_rad > screen_x)
                vertex_x = screen_x - circle_rad;

            if (vertex_y < circle_rad)
                vertex_y = circle_rad;
            if(vertex_y + circle_rad > screen_y)
                vertex_y = screen_y - circle_rad;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public class CustomDrawableView extends View {


        public CustomDrawableView(Context context) {
            super(context);
            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
        }

        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(vertex_x, vertex_y, circle_rad, mPaint);
            invalidate();

        }
    }
}
