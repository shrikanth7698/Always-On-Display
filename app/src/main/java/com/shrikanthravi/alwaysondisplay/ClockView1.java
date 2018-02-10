
package com.shrikanthravi.alwaysondisplay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by shrikanthravi on 07/11/17.
 */

public class ClockView1 extends View {

    private static final String TAG = ClockView1.class.getSimpleName();

    private static final long REDRAW_RATE = 20; // 20ms
    private static final int BACKGROUND_COLOR = 0xFF098852;

    private Paint mBackgroundPaint;
    private Paint mHandPaint;

    public ClockView1(Context context) {
        super(context);
        init(context, null);
    }

    public ClockView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(Color.TRANSPARENT);
        mBackgroundPaint.setAntiAlias(true);
        mHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHandPaint.setStrokeCap(Paint.Cap.ROUND);
        mHandPaint.setColor(Color.WHITE);
        mHandPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // always keep square size 1:1
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int measuredSize = Math.min(width, height);
        setMeasuredDimension(measuredSize, measuredSize);
    }

    /**
     * Runnable instantiated only once
     */
    private Runnable invalidator = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawMinuteHand(canvas);
        drawHourHand(canvas);
        //drawSecondHand(canvas);
        drawNail(canvas);
        // redraw itself in REDRAW_RATE millis
        postDelayed(invalidator, REDRAW_RATE);
    }

    private void drawBackground(Canvas canvas) {
        float bgrCircleRadius = getHeight() / 2f;
        canvas.drawCircle(bgrCircleRadius, bgrCircleRadius, bgrCircleRadius, mBackgroundPaint);
    }

    private void drawHourHand(Canvas canvas) {
        float viewRadius = getWidth() / 2f;
        float handRadius = getWidth() * 0.2f;
        float thickness = getWidth() * 0.06f; // 1% of view's width
        mHandPaint.setStrokeWidth(thickness);
        // coordinates of hand's end
        mHandPaint.setColor(getResources().getColor(R.color.limeBlue));
        double angle = getHoursAngle();
        float x = getStopX(viewRadius, handRadius, angle);
        float y = getStopY(viewRadius, handRadius, angle);
        canvas.drawLine(viewRadius, viewRadius, x, y, mHandPaint);
    }

    private void drawMinuteHand(Canvas canvas) {
        float viewRadius = getWidth() / 2f;
        float handRadius = getWidth() * 0.3f;
        float thickness = getWidth() * 0.05f; // 1% of view's width
        mHandPaint.setStrokeWidth(thickness);
        // coordinates of hand's end
        double angle = getMinutesAngle();
        mHandPaint.setColor(Color.WHITE);
        float x = getStopX(viewRadius, handRadius, angle);
        float y = getStopY(viewRadius, handRadius, angle);
        canvas.drawLine(viewRadius, viewRadius, x, y, mHandPaint);
    }

    private void drawSecondHand(Canvas canvas) {
        float viewRadius = getWidth() / 2f;
        float handRadius = getWidth() * 0.4f;
        float thickness = getWidth() * 0.005f; // 0.5% of view's width
        mHandPaint.setStrokeWidth(thickness);
        // coordinates of hand's end
        double angle = getSecondsAngle();
        float x = getStopX(viewRadius, handRadius, angle);
        float y = getStopY(viewRadius, handRadius, angle);
        canvas.drawLine(viewRadius, viewRadius, x, y, mHandPaint);
    }

    /**
     * Evaluates hand's end X coordinate based on the given angle.
     *      x = R + r * sin(a);
     */
    private float getStopX(float viewRadius, float handRadius, double angle) {
        return (float) (viewRadius + handRadius * Math.sin(angle));
    }

    /**
     * Evaluates hand's end Y coordinate based on the given angle
     *      y = R - r * cos(a);
     */
    private float getStopY(float viewRadius, float handRadius, double angle) {
        return (float) (viewRadius - handRadius * Math.cos(angle));
    }

    private void drawNail(Canvas canvas) {
        float viewRadius = getHeight() / 2f;
        float nailRadius = getHeight() * 0.02f;
        mHandPaint.setColor(Color.BLACK);
        canvas.drawCircle(viewRadius, viewRadius, nailRadius, mHandPaint);
    }

    /**
     * Gets angle of hour hand(minute-accurate)
     * @return angle in radians
     */
    private double getHoursAngle() {
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
        int minutesFromStart = hours * 60
                + minutes;

        return (2 * Math.PI * minutesFromStart) / 720; // divided by number of minutes in 12 hours;
    }

    /**
     * Gets angle of minute hand (second-accurate)
     * @return angle in radians
     */
    private double getMinutesAngle() {
        Calendar c = Calendar.getInstance();
        int secondsFromStart = c.get(Calendar.MINUTE) * 60
                + c.get(Calendar.SECOND);

        return (2 * Math.PI * secondsFromStart) / 3600; // divided by number of seconds in 1 hour
    }

    /**
     * Gets angle of second hand (millisecond-accurate)
     * @return angle in radians
     */
    private double getSecondsAngle() {
        Calendar c = Calendar.getInstance();
        int millisFromStart = c.get(Calendar.SECOND) * 1000 + c.get(Calendar.MILLISECOND);

        return (2 * Math.PI * millisFromStart) / 60000; // divided by number of milliseconds in 1 minute
    }
}