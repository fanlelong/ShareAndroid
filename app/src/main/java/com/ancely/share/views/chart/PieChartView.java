package com.ancely.share.views.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ancely.share.bean.PieData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.views.chart
 *  @文件名:   PieChartView
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/21 10:16 AM
 *  @描述：    TODO
 */
public class PieChartView extends View {
    //画笔
    private Paint mPaint = new Paint();
    //宽高
    private int mWidth;
    private int mHeight;
    //数据
    private ArrayList<PieData> mPieData = new ArrayList<>();
    //饼状图初始绘制角度
    private float mStartAngle = 60;

    //外部圆矩形区域
    RectF mOutRectF;

    //内部圆矩形区域
    RectF mInRectF = new RectF();

    float mOutR, mInR;//外部圆半径和外部圆半径
    //引入Path
    private Path outPath = new Path();
    private Path inPath = new Path();
    private Path outMidPath = new Path();
    private float[] mPieAngles;//角度集合
    private int mAngleId;//当前点击的id
    private int percentDecimal = 0;////百分比的小数位
    private boolean touchFlag = true;

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        //外部圆半径
        mOutR = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);// 饼状图半径
        mInR = mOutR * 0.6f;
        mOutRectF = new RectF(-mOutR, -mOutR, mOutR, mOutR);
        mInRectF = new RectF(-mInR, -mInR, mInR, mInR);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);
        if (mPieData == null || mPieData.size() == 0) {
            return;
        }
        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setAntiAlias(true);//抗锯齿
        float startAngle = mStartAngle;
        mPaint.setStyle(Paint.Style.FILL);
        for (PieData pieDatum : mPieData) {
            mPaint.setColor(pieDatum.getColor());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    outPath.moveTo(mOutR * (float) Math.cos(Math.toRadians(startAngle + pieDatum.getAngle())), mOutR * (float) Math.sin(Math.toRadians(startAngle + pieDatum.getAngle())));
            outPath.lineTo(0, 0);
            outPath.lineTo(mOutR * (float) Math.cos(Math.toRadians(startAngle)), mOutR * (float) Math.sin(Math.toRadians(startAngle)));
            outPath.arcTo(mOutRectF, startAngle, pieDatum.getAngle());

            inPath.moveTo(mInR * (float) Math.cos(Math.toRadians(startAngle + pieDatum.getAngle())), mInR * (float) Math.sin(Math.toRadians(startAngle + pieDatum.getAngle())));
            inPath.lineTo(0, 0);
            inPath.lineTo(mInR * (float) Math.cos(Math.toRadians(startAngle)), mInR * (float) Math.sin(Math.toRadians(startAngle)));
            inPath.arcTo(mInRectF, startAngle, pieDatum.getAngle());
            outPath.op(inPath, Path.Op.DIFFERENCE);
            canvas.drawPath(outPath, mPaint);
            outPath.reset();
            inPath.reset();
            startAngle += pieDatum.getAngle();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchFlag && mPieData.size() > 0) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    float x = event.getX() - (mWidth / 2);
                    float y = event.getY() - (mHeight / 2);
                    float touchAngle = 0;
                    if (x < 0 && y < 0) {
                        touchAngle += 180;
                    } else if (y < 0 && x > 0) {
                        touchAngle += 360;
                    } else if (y > 0 && x < 0) {
                        touchAngle += 180;
                    }
                    touchAngle += Math.toDegrees(Math.atan(y / x));
                    touchAngle = touchAngle - mStartAngle;
                    if (touchAngle < 0) {
                        touchAngle = touchAngle + 360;
                    }
                    float touchRadius = (float) Math.sqrt(y * y + x * x);
                    if (mInR < touchRadius && touchRadius < mOutR) {
                        mAngleId = -Arrays.binarySearch(mPieAngles, (touchAngle)) - 1;
                        Toast.makeText(getContext(), "mAngleId: " + mAngleId, Toast.LENGTH_SHORT).show();
//                        invalidate();
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    mAngleId = -1;
//                    invalidate();
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 设置数据
     *
     * @param pieData 数据
     */
    public void setPieData(ArrayList<PieData> pieData) {
        this.mPieData = pieData;
        initDate(mPieData);
    }

    private void initDate(ArrayList<PieData> pieData) {

        float dataMax = 0;
        if (pieData == null || pieData.size() == 0)
            return;
        mPieAngles = new float[pieData.size()];
        float sumValue = 0;
        for (int i = 0; i < pieData.size(); i++) {
            PieData pie = pieData.get(i);
            sumValue += pie.getValue();
        }

        float sumAngle = 0;
        for (int i = 0; i < pieData.size(); i++) {
            PieData pie = pieData.get(i);
            float percentage = pie.getValue() / sumValue;
            float angle = percentage * 360;
            pie.setPercentage(percentage);
            pie.setAngle(angle);
            sumAngle += angle;
            mPieAngles[i] = sumAngle;

            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(percentDecimal);

        }
        mAngleId = -1;
    }
}
