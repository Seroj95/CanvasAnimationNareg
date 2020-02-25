package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class ClockView extends View {
    private Paint paint;
    private Paint hourPaint;
    private Paint miniPaint;
    private Paint secondPaint;
    private Paint maxDeggre, minDeggre;
    private Canvas scanvas;
    private android.graphics.Path path;
    private TextPaint textPaint;
    private Rect textRect;
    private int width;
    private int height;
    private float hourDegre, minuteDegre, secondDegre;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);

        hourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourPaint.setStyle(Paint.Style.STROKE);
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        hourPaint.setStrokeWidth(10);
        hourPaint.setColor(context.getResources().getColor(android.R.color.holo_blue_light));

        miniPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        miniPaint.setStyle(Paint.Style.STROKE);
        miniPaint.setStrokeWidth(6);
        miniPaint.setStrokeCap(Paint.Cap.ROUND);
        miniPaint.setColor(context.getResources().getColor(android.R.color.holo_green_dark));

        secondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondPaint.setStyle(Paint.Style.STROKE);
        secondPaint.setStrokeWidth(1);
        secondPaint.setColor(context.getResources().getColor(android.R.color.holo_orange_light));

        maxDeggre = new Paint(Paint.ANTI_ALIAS_FLAG);
        maxDeggre.setColor(Color.YELLOW);
        maxDeggre.setStyle(Paint.Style.STROKE);
        maxDeggre.setStrokeWidth(6);

        scanvas = new Canvas();
        path = new Path();
        RectF rectF = new RectF(0, 100, 4, 0);
        path.addRect(rectF, Path.Direction.CCW);
        textPaint = new TextPaint();
        textPaint.setTextSize(30);
        textPaint.setColor(Color.BLACK);
        textRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        canvas.save();
        for (int i = 0; i <= 360; i += 6) {
            if (i % 30 == 0) {
                canvas.drawCircle(0, -290, 1, miniPaint);


            } else if (i % 10 == 0) {
                canvas.drawLine(0, 299, 0, 320, minDeggre);

            } else {
                canvas.drawLine(0, 300, 0, 316, miniPaint);
            }
            canvas.rotate(6);
        }
        canvas.restore();
        drawTimeText(canvas);
        getTimerDegre();
        drawHouer(canvas);
        drawMinute(canvas);
        canvas.drawCircle(0, 0, 1, miniPaint);
        drawSecond(canvas);
        invalidate();
    }

    private void drawTimeText(Canvas canvas) {
        String string = "12";

        textPaint.getTextBounds(string, 0, string.length(), textRect);
        int textWidth = textRect.width();
        String string2 = "9";
        textPaint.getTextBounds(string, 0, string2.length(), textRect);
        int textWidth2 = textRect.width();
        for (int i = 1; i <= 12; i++) {
            canvas.save();
            canvas.rotate(i * 30);
            canvas.rotate(-i * 30, 0, -320);
            canvas.translate(-5, 5);
            if (i > 9) {
                canvas.drawText(i + "", -textWidth / 2, -314, textPaint);

            } else {
                if (i > 6) {

                    canvas.drawText(i + "", textWidth2 / 2, -314, textPaint);
                } else {
                    canvas.drawText(i + "", -textWidth2 / 2, -314, textPaint);
                }
            }
            canvas.restore();
        }

    }

    private void drawHouer(Canvas canvas) {
        canvas.save();
        canvas.rotate(hourDegre, 0, 0);
        canvas.drawLine(0, 0, 0, -228, hourPaint);
        canvas.restore();
    }

    private void drawMinute(Canvas canvas) {
        canvas.save();
        canvas.rotate(minuteDegre, 0, 0);
        canvas.drawLine(0, 0, 0, -270, miniPaint);
        canvas.restore();
    }
    private void drawSecond(Canvas canvas) {
        canvas.save();
        canvas.rotate(secondDegre, 0, 0);
        canvas.drawLine(0, 0, 0, -270, secondPaint);
        canvas.restore();
    }
    private void getTimerDegre() {
        Calendar calendar = Calendar.getInstance();
        float second = calendar.get(Calendar.SECOND);
        float minute = calendar.get(Calendar.MINUTE);
        float hour = calendar.get(Calendar.HOUR);
        secondDegre = second / 60 * 360;
        minuteDegre = minute / 60 * 360;
        hourDegre = hour / 12 * 360;
    }


}

