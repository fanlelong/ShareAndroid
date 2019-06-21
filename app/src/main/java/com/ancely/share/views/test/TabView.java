package com.ancely.share.views.test;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancely.share.R;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.views.test
 *  @文件名:   TabView
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/17 2:25 PM
 *  @描述：    底部Tab
 */
public class TabView extends FrameLayout {
    private TextView mTvTitle;
    private ImageView mTabIv;
    private static final int COLOR_DEFAULT = Color.parseColor("#ff666666");
    private static int COLOR_SELECT = Color.parseColor("#FF0F66CC");

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_tabview, this);
        mTvTitle = findViewById(R.id.tab_tv_title);
        mTabIv = findViewById(R.id.tab_iv);
        setProcess(0);
    }

    public void setProcess(float process) {
        mTvTitle.setTextColor(evaluate(process, COLOR_DEFAULT, COLOR_SELECT));
        mTabIv.setColorFilter(evaluate(process, COLOR_DEFAULT, COLOR_SELECT));
    }

    public void setTest(String title) {
        mTvTitle.setText(title);
    }

    public int evaluate(float fraction, int startValue, int endValue) {
        float startA = (float) (startValue >> 24 & 255) / 255.0F;
        float startR = (float) (startValue >> 16 & 255) / 255.0F;
        float startG = (float) (startValue >> 8 & 255) / 255.0F;
        float startB = (float) (startValue & 255) / 255.0F;

        float endA = (float) (endValue >> 24 & 255) / 255.0F;
        float endR = (float) (endValue >> 16 & 255) / 255.0F;
        float endG = (float) (endValue >> 8 & 255) / 255.0F;
        float endB = (float) (endValue & 255) / 255.0F;
        startR = (float) Math.pow((double) startR, 2.2D);
        startG = (float) Math.pow((double) startG, 2.2D);
        startB = (float) Math.pow((double) startB, 2.2D);
        endR = (float) Math.pow((double) endR, 2.2D);
        endG = (float) Math.pow((double) endG, 2.2D);
        endB = (float) Math.pow((double) endB, 2.2D);
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);
        a *= 255.0F;
        r = (float) Math.pow((double) r, 0.45454545454545453D) * 255.0F;
        g = (float) Math.pow((double) g, 0.45454545454545453D) * 255.0F;
        b = (float) Math.pow((double) b, 0.45454545454545453D) * 255.0F;
        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
