package com.ancely.share.views.banner;

/*
 *  @项目名：  BaseMvp
 *  @包名：    mvp.ancely.com.myapplication.widget
 *  @文件名:   CustomViewpager
 *  @创建者:   fanlelong
 *  @创建时间:  2018/8/21 下午5:48
 *  @描述：    可以设置是否滚动的ViewPager
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewpager extends ViewPager {
    private boolean isCanScroll=true;
    private OnItemClickListener onItemClickListener;
    private float oldX = 0, newX = 0;
    private static final float sens = 5;
    public CustomViewpager(@NonNull Context context) {
        super(context);
    }

    public CustomViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (isCanScroll)
            return super.onInterceptTouchEvent(ev);
        else
            return false;

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            if (onItemClickListener != null) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        newX = ev.getX();
                        if (Math.abs(oldX - newX) < sens) {
                            onItemClickListener.onItemClick(getCurrentItem());
                        }
                        oldX = 0;
                        newX = 0;
                        break;
                }
            }
            return super.onTouchEvent(ev);
        } else
            return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.isCanScroll = enabled;
    }

    public void setOnItemClickListener(OnItemClickListener l){
        this.onItemClickListener = l;
    }
}
