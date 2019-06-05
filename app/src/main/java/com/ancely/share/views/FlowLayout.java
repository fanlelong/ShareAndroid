package com.ancely.share.views;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.ancely.share.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;


/*
 * 自定义流式布局
 */
public class FlowLayout extends ViewGroup {

    /**
     * 定义一个指示器代表的哪一行
     */
    Line mCurrent;

    /**
     * 定义一个存储行的集合
     */
    List<Line> mLineDatas;

    /**
     * View和View的左边距的默认值
     */
    public static int NORMOL_MARGIN_LEFT = SizeUtils.px2dp(10);

    /**
     * 行与行之间的间距的默认值
     */
    public static int NORMOL_MARGIN_TOP = SizeUtils.px2dp(10);
    private GradientDrawable mNormolDrawable;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public void setMarginTop(int px) {
        NORMOL_MARGIN_TOP = SizeUtils.px2dp(px);
    }

    public void setlMarginLeft(int px) {
        NORMOL_MARGIN_LEFT = SizeUtils.px2dp(px);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLineDatas = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mLineDatas.clear();
        mCurrent = null;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        //计算出可用的最大宽度
        int maxWidth = width - getPaddingLeft() - getPaddingRight();

        //测量孩子的宽高
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //如果为空则代表是添加第一个孩子,所以不要判断是否可以添加
            if (mCurrent == null) {
                mCurrent = new Line(maxWidth, NORMOL_MARGIN_LEFT);
                mLineDatas.add(mCurrent);
                mCurrent.startAddView(childView);

            } else {
                //要判断是否可以添加
                if (mCurrent.isCanAddView(childView)) {
                    //可以添加
                    mCurrent.startAddView(childView);
                } else {
                    //不可以添加
                    mCurrent = new Line(maxWidth, NORMOL_MARGIN_LEFT);
                    mCurrent.startAddView(childView);
                    mLineDatas.add(mCurrent);
                }
            }
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClidlClick(v);
                    }
                }
            });

        }
        //开始设置自己的高
        //初始的为二个pading值相加
        int height = getPaddingBottom() + getPaddingTop();
        for (int i = 0; i < mLineDatas.size(); i++) {
            //把每行的高度和之间的间距加起来
            Line line = mLineDatas.get(i);


            if (i == mLineDatas.size() - 1) {
                height += line.mLineHeight;
            } else {
                height += line.mLineHeight + NORMOL_MARGIN_TOP;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int top = getPaddingTop();
        int left = getPaddingLeft();
        for (Line line : mLineDatas) {
            line.onLayout(top, left);
            top = top + line.mLineHeight + NORMOL_MARGIN_TOP;
        }
    }


    private class Line {

        /**
         * 每行中孩子View的集合
         */
        private List<View> mChildList;
        /**
         * View和View之间的marginleft
         */
        private int mMarginLeft = 15;
        /**
         * 每行的高度
         */
        private int mLineHeight;

        /**
         * 可用的宽度空间
         */
        private int mUsedWidthSpeace;

        /**
         * 每行宽度的最大值
         */
        private int mMaxWidthSpeace;

        Line(int widthMax, int marginLeft) {
            mMaxWidthSpeace = widthMax;
            mMarginLeft = marginLeft;
            mChildList = new ArrayList<>();
        }

        /**
         * 判断是否可以添加View
         */
        boolean isCanAddView(View v) {

            int childWidth = v.getMeasuredWidth();
            //如果进来时size为0则证明是每行的每一个View
            return mChildList.size() == 0 || mUsedWidthSpeace + childWidth + mMarginLeft < mMaxWidthSpeace;
        }

        /**
         * 开始添加孩子
         */
        void startAddView(View v) {
            int childWidth = v.getMeasuredWidth();
            int childHeight = v.getMeasuredHeight();

            if (mChildList.size() == 0) {//证明是第一个孩子
                mUsedWidthSpeace = childWidth;//用了的就等于孩子的宽度
                mLineHeight = childHeight;//高度则等于该View的宽度
            } else {
                //证明添加的是从每行的第二个开始
                mUsedWidthSpeace += childWidth + mMarginLeft;
                mLineHeight = Math.max(mLineHeight, childHeight);
            }
            mChildList.add(v);
        }

        void onLayout(int top, int left) {

            //求出剩余空间是多少
//            int usableSpeaceWidth = mMaxWidthSpeace - mUsedWidthSpeace;
//            int aver = usableSpeaceWidth / mChildList.size();
//            LogUtils.d("aver" + aver);
            for (View v : mChildList) {
                int childWidth = v.getMeasuredWidth();
                int childHeight = v.getMeasuredHeight();

//                if (aver > 0) {

//                    int childWidthMeaSpec = MeasureSpec.makeMeasureSpec(childWidth + aver, MeasureSpec.EXACTLY);
//                    int childheightMeaSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
//                    v.measure(childWidthMeaSpec, childheightMeaSpec);

//                    childWidth = v.getMeasuredWidth();
//                    childHeight = v.getMeasuredHeight();
//                }

                v.layout(left, top, left + childWidth, top + childHeight);
                left = left + childWidth + NORMOL_MARGIN_LEFT;

            }
        }
    }

    private OnChildClickListener listener;

    public void setOnChildClickListener(OnChildClickListener l) {
        this.listener = l;
    }

    public interface OnChildClickListener {
        void onClidlClick(View view);
    }

//    int color[] = new int[]{0xffffc438, 0xff80cbc4, 0xff87c9e0, 0xffd1abe5, 0xffffc438, 0xff80cbc4, 0xff87c9e0, 0xffd1abe5};


//    @SuppressWarnings("deprecation")
//    public void addTextView(List<String> lists) {
//        removeAllViews();
//        for (String s : lists) {
//            if (GeneralUtils.isEmpty(s)) {
//                continue;
//            }
//            GradientDrawable normolDrawable = DrawableUtils.creatDrable(getContext(), R.color.colro_fff3f4, 2);
//            TextView textView = new TextView(getContext());
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, SizeUtils.px2dp(20));
//            textView.setBackgroundDrawable(normolDrawable);
//            textView.setPadding(SizeUtils.px2dp(16), SizeUtils.px2dp(6), SizeUtils.px2dp(15), SizeUtils.px2dp(6));
//            textView.setTextColor(getContext().getResources().getColor(R.color.colro_e52e3d));
//            textView.setText(s);
//            addView(textView);
//        }
//    }
//
//    @SuppressWarnings("deprecation")
//    public void addTextViewC(List<String> lists) {
//        removeAllViews();
//        for (String s : lists) {
//            s = "" + s;
//            if (GeneralUtils.isEmpty(s)) {
//                continue;
//            }
//            GradientDrawable normolDrawable = DrawableUtils.creatDrable(getContext(), R.color.color_f0f0f0, 2);
//            TextView textView = new TextView(getContext());
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, SizeUtils.px2dp(22));
//            textView.setBackgroundDrawable(normolDrawable);
//            textView.setPadding(SizeUtils.px2dp(7), SizeUtils.px2dp(3), SizeUtils.px2dp(7), SizeUtils.px2dp(3));
//            textView.setTextColor(getContext().getResources().getColor(R.color.color_999999));
//
//            textView.setText(s);
//            addView(textView);
//            ContextCompat.getColor(getContext(), R.color.color_333333);
//        }
//    }
}
