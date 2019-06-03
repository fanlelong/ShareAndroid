package com.ancely.share.views;

/*
 *  @项目名：  BaseMvp
 *  @包名：    mvp.ancely.com.myapplication.widget
 *  @创建者:   fanlelong
 *  @创建时间:  2018/8/21 下午2:49
 *  @描述：    TODO
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ancely.share.R;
import com.ancely.share.utils.SizeUtils;

public class BottomView extends LinearLayout {

    private TextView mTextView;
    private ImageView mImageView;

    public BottomView(Context context) {
        this(context, null);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomView);
        String text = a.getString(R.styleable.BottomView_bvText);
        int imgRes = a.getResourceId(R.styleable.BottomView_bvIcon, R.drawable.ic_home_black_24dp);
        a.recycle();
        mImageView = new ImageView(context);
        MarginLayoutParams ivParams = new MarginLayoutParams(SizeUtils.px2dp(25), SizeUtils.px2dp(21));
        mImageView.setImageResource(imgRes);
        addView(mImageView, ivParams);

        mTextView = new TextView(context);
        MarginLayoutParams tvParams = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.topMargin = SizeUtils.px2dpH(1);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, SizeUtils.px2dpH(12));
        mTextView.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
        mTextView.setText(text);
        addView(mTextView, tvParams);
    }

    public void changerViewColor(boolean isSelect) {
        mTextView.setTextColor(ContextCompat.getColor(getContext(), isSelect ? R.color.colorPrimary : R.color.color_666666));
        if (isSelect) {
            mImageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        } else {
            mImageView.clearColorFilter();
        }
    }
}
