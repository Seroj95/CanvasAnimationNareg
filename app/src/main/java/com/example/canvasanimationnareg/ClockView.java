package com.example.canvasanimationnareg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class ClockView extends View {
    private int height = 0;
    private int width = 0;
    private int fontSize = 0;
    private int numeralSpacinf = 0;
    private int handTruncatin = 0, hourHandTruncation = 0;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;
    private int padding = 0;
    private Rect rect = new Rect();
    private int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initClock();
        }
        canvas.drawColor(Color.YELLOW);
        drawCicrce(canvas);
        drawCenter(canvas);
        drawNaumerla(canvas);
        drawHands(canvas);
        postInvalidateDelayed(500);
        invalidate();

    }

    private void drawHand(Canvas canvas, double loc, boolean isHour) {
        double angle = Math.PI * loc / 30 - Math.PI / 2;
        int handRadious = isHour ? radius - handTruncatin - hourHandTruncation : radius - handTruncatin;
        canvas.drawLine(width / 2, height / 2, (float) (width / 2 + Math.cos(angle) * handRadious),
                (float) (height / 2 + Math.sin(angle) * handRadious),
                paint);
    }

    private void drawHands(Canvas canvas) {
Calendar calendar =Calendar.getInstance();
float hour=calendar.get(Calendar.HOUR_OF_DAY);
hour=hour>12?hour-12:hour;
drawHand(canvas,(hour+ calendar.get(Calendar.MINUTE)/60)*5f,true);
drawHand(canvas,calendar.get(Calendar.MINUTE),false);
drawHand(canvas,calendar.get(Calendar.SECOND),false);
    }

    private void drawNaumerla(Canvas canvas) {
        paint.setTextSize(fontSize);
        for (int number : numbers) {
            String tpm = String.valueOf(number);
            paint.getTextBounds(tpm, 0, tpm.length(), rect);
            double angl = Math.PI / 6 * (number - 3);
            int x = (int) (width / 2 + Math.cos(angl) * radius - rect.width() / 2);
            int y = (int) (height / 2 + Math.sin(angl) * radius + rect.height() / 2);
            canvas.drawText(tpm, x, y, paint);
        }
    }


    private void drawCenter(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, 12, paint);
    }

    private void drawCicrce(Canvas canvas) {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.holo_red_light));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, radius + padding - 10, paint);
    }

    private void initClock() {
        height = getHeight();
        width = getWidth();
        padding = numeralSpacinf + 50;
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics());
        int min = Math.min(height, width);
        radius = min / 2 - padding;
        handTruncatin = min / 7;
        paint = new Paint();
        isInit = true;


    }
}
