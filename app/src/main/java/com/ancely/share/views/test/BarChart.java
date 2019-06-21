package com.ancely.share.views.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.views.test
 *  @文件名:   BarChart
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/20 9:38 AM
 *  @描述：    TODO
 */
public class BarChart extends View {

    private Paint mLinePaint;//线画笔
    private Paint mChartPaint;//柱子画笔
    public static final int DEFAULT_LEFT_MARGIN = 180;
    public static final int DEFAULT_RIGHT_MARGIN = 120;
    private int mLeftMargin = 180;
    private int mRightMargin = 120;
    private int mWidth;
    private int mHeight;
    private Path mPath;

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mLinePaint = new Paint();
        //初始化画笔
        mLinePaint.setStyle(Paint.Style.FILL);//设置画笔类型
        mLinePaint.setAntiAlias(true);//抗锯齿
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStrokeWidth(10);
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);//抗锯齿
        mChartPaint.setColor(Color.BLUE);//设置画笔颜色
        mChartPaint.setTextSize(50);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mWidth = MeasureSpec.getSize(widthMeasureSpec);
//        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;


    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(60, mHeight - 120);
        canvas.drawPoint(0, 0, mLinePaint);

        mLinePaint.setStrokeWidth(1);
        canvas.drawLine(0, 0, mWidth - 120, 0, mLinePaint);

        canvas.drawLine(0, 0, 0, -mWidth * 0.8f + 120, mLinePaint);
//
        canvas.drawLine(mWidth - 120, 0, (mWidth - 120) * 0.98f, (mWidth - 120) * 0.02f, mLinePaint);
        canvas.drawLine(mWidth - 120, 0, (mWidth - 120) * 0.98f, -(mWidth - 120) * 0.02f, mLinePaint);
//
        canvas.drawLine(0, -mWidth * 0.8f + 120, (-mWidth * 0.8f + 120) * 0.02f, (-mWidth * 0.8f + 120) * 0.98f, mLinePaint);
        canvas.drawLine(0, -mWidth * 0.8f + 120, -(-mWidth * 0.8f + 120) * 0.02f, (-mWidth * 0.8f + 120) * 0.98f, mLinePaint);
        int linwWidth = mWidth - 120;
        int charHeight = (int) (-mWidth * 0.8f + 120);
        int num = linwWidth / 240;
        int heightNum = -charHeight / 240;
        for (int i = 1; i < num; i++) {
            canvas.drawLine(linwWidth * 1.f * i / num, 0, linwWidth * 1.f * i / num, -10, mLinePaint);

        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{8, 4, 16, 4}, 6));
        for (int i = 0; i < heightNum; i++) {
            canvas.drawLine(0, charHeight * 1.f * i / heightNum, linwWidth, charHeight * 1.f * i / heightNum, mLinePaint);
        }
        mLinePaint.setPathEffect(null);
    }
}
