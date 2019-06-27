package com.ancely.share.views.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.ancely.share.R;
import com.ancely.share.utils.SizeUtils;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.views.test
 *  @文件名:   RectItem
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/24 5:41 PM
 *  @描述：    TODO
 */
public class RectItem extends View {
    private Paint mPaint;//柱形画笔
    private Paint mBgPaint;//顶部背影画笔
    private Paint mTextPaint;//底部字体画笔

    private Path mPath;
    int mHeight, mMarginBottom;
    private Bitmap mBitmap;

    public static final int mDefaultR = 16;
    private int mChartLeftMargin;
    private int mLeftMargin = 90;
    private float mChartHeight = 192;
    private int mCirlR = mDefaultR;
    private int mBitmapCircle = mDefaultR * 3;
    private RectF mRect;
    private static int mBottomHeight = SizeUtils.px2dp(41);
    private Path mBgPath;
    private Path mBgRectPath;
    private LinearGradient mLinearGradient;

    public RectItem(Context context) {
        this(context, null);
    }

    public RectItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        //初始化画笔
        mPaint.setStyle(Paint.Style.FILL);//设置画笔类型
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(0xff1ad2a9);
        mPaint.setStrokeWidth(1);

        mBgPaint = new Paint();
        mBgPaint.setColor(0xffFFB031);
        mBgPaint.setStyle(Paint.Style.FILL);//设置画笔类型
        mBgPaint.setAntiAlias(true);//抗锯齿


        mTextPaint = new Paint();
        mTextPaint.setTextSize(SizeUtils.px2dp(12));
        mTextPaint.setColor(0xff050505);
        mTextPaint.setAntiAlias(true);//抗锯齿
        mPath = new Path();
        mBgPath = new Path();
        mBgRectPath = new Path();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.point);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mBottomTitleRect != null) {
            int width = mLeftMargin + mBottomTitleRect.width() + 164 - mBottomTitleRect.width() / 2 + 16;
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mMarginBottom = mHeight - mBottomHeight;
        mLinearGradient = new LinearGradient(-164, 0, 164, 0, 0xffFFD273, 0xffFFB031, Shader.TileMode.CLAMP);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (TextUtils.isEmpty(mBottomTitle)) {
            return;
        }
        mChartHeight = mHeight - mBottomHeight - mBottomHeight * mPresent * 5 + mCirlR;

        canvas.drawText(mBottomTitle, mLeftMargin, mHeight - (mBottomHeight - mBottomTitleRect.height()) / 2, mTextPaint);

        mPath.moveTo(mChartLeftMargin, mMarginBottom);
        mPath.lineTo(mChartLeftMargin, mChartHeight);
        mPath.addArc(mChartLeftMargin, mChartHeight - mCirlR, mChartLeftMargin + 2 * mCirlR, mChartHeight + mCirlR, -180, 180);
        mPath.lineTo(mChartLeftMargin + 2 * mCirlR, mMarginBottom);
        mPath.lineTo(mChartLeftMargin, mMarginBottom);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();

        if (mIsVisibility) {
            mRect = new RectF(mChartLeftMargin + mCirlR - mBitmapCircle, mChartHeight - mBitmapCircle, mChartLeftMargin + mCirlR + mBitmapCircle, mChartHeight + mBitmapCircle);
            canvas.drawBitmap(mBitmap, null, mRect, mPaint);

            //绘制顶部橙色背影
            canvas.save();
            canvas.translate(mChartLeftMargin + mCirlR, mChartHeight - 3 * mCirlR - 30 - 5);
            canvas.rotate(45);
            mBgPath.addRoundRect(-25f, -25, 25, 25, 8, 8, Path.Direction.CCW);
//            canvas.drawRect(-25f, -25, 25, 25, mPaint);
            canvas.drawPath(mBgPath, mBgPaint);
            canvas.restore();
            mBgPath.reset();

            canvas.save();
            canvas.translate(mChartLeftMargin + mCirlR, mChartHeight - 78 - 3 * mCirlR);
            mBgRectPath.addRoundRect(-164, -48f, 164f, 48f, 48, 48, Path.Direction.CCW);
            mBgPaint.setShader(mLinearGradient);
            canvas.drawPath(mBgRectPath, mBgPaint);
            mBgPath.reset();
            canvas.restore();
        }
    }

    private String mBottomTitle;
    private Rect mBottomTitleRect;
    private float mPresent;
    private boolean mIsVisibility = true;

    public void setText(String title, float present) {
        mBottomTitleRect = new Rect();
        mPresent = present;
        mTextPaint.getTextBounds(title, 0, title.length(), mBottomTitleRect);
        mBottomTitle = title;
        mChartLeftMargin = mLeftMargin + (mBottomTitleRect.width() - mCirlR) / 2;
        requestLayout();
    }

    public void setLeftMargin(int leftMargin) {
        mLeftMargin = leftMargin;
        requestLayout();
    }

    public void setTopBitmapVisbility(boolean isVisibility) {
        mIsVisibility = isVisibility;
    }

    public void bringToFronts() {
        bringToFront();
    }
}
