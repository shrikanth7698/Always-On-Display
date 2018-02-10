package com.shrikanthravi.alwaysondisplay;

/**
 * Created by shrikanthravi on 07/11/17.
 */


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

public class EdgeLight extends View {


    /**
     * ProgressBar's line thickness
     */

    private int animSpeed=1000;
    private float strokeWidth = 4;
    private float progress = 0;
    private int min = 0;
    private int max = 100;
    /**
     * Start the progress at 12 o'clock
     */
    private int startAngle = -90;
    private int color = Color.DKGRAY;
    private int bgColor = Color.TRANSPARENT;
    private RectF rectF;
    private Paint backgroundPaint;
    private Paint foregroundPaint;

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        backgroundPaint.setStrokeWidth(strokeWidth);
        foregroundPaint.setStrokeWidth(strokeWidth);
        invalidate();
        requestLayout();//Because it should recalculate its bounds
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        backgroundPaint.setColor(adjustAlpha(color,0.5f));
        foregroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }
    public void setbgColor(int color) {
        this.bgColor = color;
        backgroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }
    public EdgeLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rectF = new RectF();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EdgeLight,
                0, 0);
        //Reading values from the XML layout
        try {
            strokeWidth = typedArray.getDimension(R.styleable.EdgeLight_eprogressBarThickness, strokeWidth);
            progress = typedArray.getFloat(R.styleable.EdgeLight_eprogress, progress);
            color = typedArray.getInt(R.styleable.EdgeLight_eprogressbarColor, color);
            min = typedArray.getInt(R.styleable.EdgeLight_emin, min);
            max = typedArray.getInt(R.styleable.EdgeLight_emax, max);
        } finally {
            typedArray.recycle();
        }

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(adjustAlpha(color,0.5f));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);

        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setColor(color);
        foregroundPaint.setStrokeCap(Paint.Cap.ROUND);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(28);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "google_font.ttf");
        paint.setTypeface(font);
        paint.setColor(Color.BLACK);
        //canvas.drawOval(rectF, backgroundPaint);
        float angle = 360 * progress / max;
        float angle1 = 360 * 1/max;
        //canvas.drawArc(rectF, startAngle, angle, false, foregroundPaint);
        //canvas.drawCircle(rectF.centerX(),rectF.top,1,foregroundPaint);
        int progressText = (int) Math.ceil(progress);
        //canvas.drawText(Integer.toString(progressText),rectF.centerX()-10,rectF.top+10,paint);
        canvas.drawLine(rectF.centerX()-progress,rectF.centerY(),rectF.centerX()+progress,rectF.centerY(),foregroundPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min/8);
        rectF.set(0 + strokeWidth, 0 + strokeWidth / 2, min - strokeWidth / 2, min/8 - strokeWidth / 2);
    }

    public int lightenColor(int color, float factor) {
        float r = Color.red(color) * factor;
        float g = Color.green(color) * factor;
        float b = Color.blue(color) * factor;
        int ir = Math.min(255, (int) r);
        int ig = Math.min(255, (int) g);
        int ib = Math.min(255, (int) b);
        int ia = Color.alpha(color);
        return (Color.argb(ia, ir, ig, ib));
    }


    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public void setAnimationSpeed(int speed){
        animSpeed=speed;
    }

    public void setProgressWithAnimation(float progress) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(animSpeed);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }


}

