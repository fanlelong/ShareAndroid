package com.ancely.share.views.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


/**
 * Created by xuekai on 2016/11/10.
 */

public class Item extends View {
    private int maxValue;//最高值
    private int minValue;//最低值
    private int currentValue = 15;//当前值
    private int lastValue = 10;//上一个值
    private int nextValue = 10;//下一个值
    private Paint mPaint;
    private int viewHeight;
    private int viewWidth;
    private int pointTopY = 130;//最高点的Y坐标 130
    private int pointBottomY = 255;//最低点的Y坐标 255
    private int pointX;//所有点的x坐标
    private int pointY;//当前点的Y

    private boolean drawLeftLine = true;//是否画左边的线
    private boolean drawRightLine = true;//是否画右边的线

    public Item(Context context) {
        super(context);
        init();
        setBackgroundColor(0xffffffff);
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        invalidate();
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initValues();
    }

    private void initValues() {
        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();
        pointX = viewWidth / 2;

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        setBackgroundColor(0x00ff0000);
        pointY = (int) ((pointBottomY - pointTopY) * 1f / (maxValue - minValue) * (maxValue - currentValue + minValue) + pointTopY);

//        drawLine(canvas);
        drawGraph(canvas);
        drawPoint(canvas);
        drawValue(canvas);
//        Log.e("Item", "" + lastValue + " " + currentValue + " " + nextValue);
    }

    /**
     * 画数字
     *
     * @param canvas
     */
    private void drawValue(Canvas canvas) {
        mPaint.setTextSize(20);
        mPaint.setColor(Color.parseColor("#ff333333"));
        mPaint.setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseLine1 = pointY - fontMetrics.bottom * 4;
        canvas.drawText(currentValue + "", viewWidth / 2, baseLine1, mPaint);
    }

    public void setlastValue(int lastValue) {
        this.lastValue = lastValue;
    }

    public void setNextValue(int nextValue) {
        this.nextValue = nextValue;
    }

    /**
     * 画折线
     *
     * @param canvas
     */
    private void drawGraph(Canvas canvas) {

        mPaint.setPathEffect(null);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xff24C3F1);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        if (drawLeftLine) {
            float middleValue = currentValue - (currentValue - lastValue) / 2f;
            float middleY = ((pointBottomY - pointTopY) * 1f / (maxValue - minValue) * (maxValue - middleValue + minValue) + pointTopY);
            canvas.drawLine(0, middleY, pointX, pointY, mPaint);
        }
        if (drawRightLine) {
            float middleValue = currentValue - (currentValue - nextValue) / 2f;
            float middleY = ((pointBottomY - pointTopY) * 1f / (maxValue - minValue) * (maxValue - middleValue + minValue) + pointTopY);
            canvas.drawLine(pointX, pointY, viewWidth, middleY, mPaint);
        }
    }

    /**
     * 画数字下面的小圆圈
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        mPaint.setColor(0xffffffff);
        mPaint.setPathEffect(null);

        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(pointX, pointY, 10, mPaint);
        mPaint.setColor(0xff24C3F1);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(pointX, pointY, 5, mPaint);
    }

    /**
     * 画最下面的一条线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        mPaint.setColor(0xffff0000);
        mPaint.setPathEffect(null);

        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.FILL);
        if (drawLeftLine) {
            canvas.drawLine(0, 500, viewWidth / 2, 500, mPaint);//这两个值表示下面那条横线的Y坐标（第二四个参数）

        }
        if (drawRightLine) {
            canvas.drawLine(viewWidth / 2, 500, viewWidth, 500, mPaint);//这两个值表示下面那条横线的Y坐标（第二四个参数）

        }
    }

    public void setDrawLeftLine(boolean drawLeftLine) {
        this.drawLeftLine = drawLeftLine;
    }

    public void setDrawRightLine(boolean drawRightLine) {
        this.drawRightLine = drawRightLine;
    }


}