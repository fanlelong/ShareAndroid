package com.ancely.share.views.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ancely.share.R;
import com.ancely.share.bean.PieData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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
    private float mStartAngle = -90;
    //外部圆矩形区域
    RectF mOutRectF, mToutRectF;

    //内部圆矩形区域
    RectF mInRectF;

    //中心图片区域
    RectF mCenterRectF;

    float mOutR, mInR, mToutR, mCenterR;//外部圆半径和外部圆半径
    //引入Path
    private Path outPath = new Path();
    private Path inPath = new Path();
    private Path outMidPath = new Path();
    private float[] mPieAngles;//角度集合
    private int mAngleId;//当前点击的id
    private int percentDecimal = 0;////百分比的小数位
    private boolean touchFlag = true;
    private Bitmap mCenterBitmap;

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCenterBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        //外部圆半径
        mToutR = Math.min(mWidth, mHeight) / 2 * 0.8f * 0.7f;
        mOutR = Math.min(mWidth, mHeight) / 2 * 0.8f * 0.6f;// 饼状图半径
        mInR = mOutR * 0.8f;
        mCenterR = mInR * 0.9f;
        mOutRectF = new RectF(-mOutR, -mOutR, mOutR, mOutR);
        mToutRectF = new RectF(-mToutR, -mToutR, mToutR, mToutR);
        mInRectF = new RectF(-mInR, -mInR, mInR, mInR);
        mCenterRectF = new RectF(-mCenterR, -mCenterR, mCenterR, mCenterR);
        mGlobalRegion = new Region(-(int) mOutR, -(int) mOutR, (int) mOutR, (int) mOutR);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);
        setBackgroundColor(0xffffffff);
        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setAntiAlias(true);//抗锯齿
        float startAngle = mStartAngle;
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawBitmap(mCenterBitmap, null, mCenterRectF, mPaint);
        if (mPieData == null || mPieData.size() == 0) {
            mPaint.setColor(0x99999999);
            inPath.addCircle(0, 0, mInR, Path.Direction.CW);
            outPath.addCircle(0, 0, mOutR, Path.Direction.CW);
            outPath.op(inPath, Path.Op.DIFFERENCE);
            canvas.drawPath(outPath, mPaint);
            outPath.reset();
            inPath.reset();
            return;
        }

        if (mPieData.size() == 1) {
            PieData pieDatum = mPieData.get(0);

            mPaint.setColor(pieDatum.getColors().get(0));
            inPath.addCircle(0, 0, mInR, Path.Direction.CW);
            if (mAngleId == 0) {
                outPath.addCircle(0, 0, mToutR, Path.Direction.CW);
            } else {
                outPath.addCircle(0, 0, mOutR, Path.Direction.CW);
            }
            outPath.op(inPath, Path.Op.DIFFERENCE);
            Region region = mRegions.get(0);
            region.setPath(outPath, mGlobalRegion);
            SweepGradient sweepGradient = new SweepGradient(0, 0, pieDatum.getColors().get(0), pieDatum.getColors().get(1));
            mPaint.setShader(sweepGradient);
            canvas.drawPath(outPath, mPaint);
            outPath.reset();
            inPath.reset();
            return;
        }

        for (int i = 0; i < mPieData.size(); i++) {
            PieData pieDatum = mPieData.get(i);
            if (mAngleId == i) {
                drawArc(canvas, pieDatum, startAngle, mToutR, mToutRectF, mRegions.get(i));
            } else {
                drawArc(canvas, pieDatum, startAngle, mOutR, mOutRectF, mRegions.get(i));
            }
            startAngle += pieDatum.getAngle();
        }
    }

    private Region mGlobalRegion;

    private void drawArc(Canvas canvas, PieData pieDatum, float startAngle, float outR, RectF outRectF, Region region) {
        mPaint.setColor(pieDatum.getColors().get(0));
        inPath.moveTo(outR * (float) Math.cos(Math.toRadians(startAngle + pieDatum.getAngle())), outR * (float) Math.sin(Math.toRadians(startAngle + pieDatum.getAngle())));

        outPath.lineTo(0, 0);
        outPath.lineTo(outR * (float) Math.cos(Math.toRadians(startAngle)), outR * (float) Math.sin(Math.toRadians(startAngle)));
        outPath.arcTo(outRectF, startAngle, pieDatum.getAngle());

        inPath.moveTo(mInR * (float) Math.cos(Math.toRadians(startAngle + pieDatum.getAngle())), mInR * (float) Math.sin(Math.toRadians(startAngle + pieDatum.getAngle())));
        inPath.lineTo(0, 0);
        inPath.lineTo(mInR * (float) Math.cos(Math.toRadians(startAngle)), mInR * (float) Math.sin(Math.toRadians(startAngle)));
        inPath.arcTo(mInRectF, startAngle, pieDatum.getAngle());
        outPath.op(inPath, Path.Op.DIFFERENCE);
        LinearGradient gradient = new LinearGradient(outR * (float) Math.cos(Math.toRadians(startAngle)), outR * (float) Math.sin(Math.toRadians(startAngle)), mInR * (float) Math.cos(Math.toRadians(startAngle + pieDatum.getAngle())), mInR * (float) Math.sin(Math.toRadians(startAngle + pieDatum.getAngle())), pieDatum.getColors().get(0), pieDatum.getColors().get(1), Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        region.setPath(outPath, mGlobalRegion);
        canvas.drawPath(outPath, mPaint);
        mPaint.setShader(null);
        outPath.reset();
        inPath.reset();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchFlag && mPieData.size() > 0) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    float x = event.getX() - (mWidth / 2);
                    float y = event.getY() - (mHeight / 2);
                    for (int i = 0; i < mRegions.size(); i++) {
                        Region region = mRegions.get(i);
                        if (region.contains((int) x, (int) y)) {
                            Toast.makeText(getContext(), "mAngleId: " + i, Toast.LENGTH_SHORT).show();
                            if (mAngleId==i) {
                                mAngleId=-1;
                            }else{
                                mAngleId = i;
                            }
                            invalidate();
                            return true;
                        }
                    }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
//                    mAngleId = -1;
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

    private List<Region> mRegions = new ArrayList<>();

    private void initDate(ArrayList<PieData> pieData) {
        mRegions.clear();
        if (pieData == null || pieData.size() == 0) return;
        mPieAngles = new float[pieData.size()];
        float sumValue = 0;
        for (int i = 0; i < pieData.size(); i++) {
            PieData pie = pieData.get(i);
            sumValue += pie.getValue();
            Region region = new Region();
            mRegions.add(region);
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
