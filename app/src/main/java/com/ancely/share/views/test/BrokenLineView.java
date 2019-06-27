package com.ancely.share.views.test;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.ancely.share.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xk on 2016/11/9 22:14.
 */

public class BrokenLineView extends RecyclerView {

    private int maxValue;
    private int minValue;
    private List<ChartBean> data = new ArrayList<>();
    private Adapter adapter;
    private Paint mLinePaint;
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;
    private Rect mTextBound;
    private List<String> present = new ArrayList<>();
    private int mPaddingLeft, mPaddingRight;

    public BrokenLineView(Context context) {
        this(context, null);

    }

    public BrokenLineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        present.add("-20%");
        present.add("0%");
        present.add("20%");
        present.add("40%");
        present.add("60%");
        present.add("80%");
        present.add("100%");

        mLinePaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(SizeUtils.px2dp(10));
        mTextPaint.setColor(0xffc8c8c8);
        mTextPaint.setAntiAlias(true);//抗锯齿
        mTextBound = new Rect();

        //初始化画笔
        mLinePaint.setStyle(Paint.Style.FILL);//设置画笔类型
        mLinePaint.setAntiAlias(true);//抗锯齿
        mLinePaint.setColor(0xffe6e6e6);
        mLinePaint.setStrokeWidth(10);


        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new Adapter();
        setAdapter(adapter);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();

        mWidth = w - mPaddingLeft / 2 - mPaddingRight;
        mHeight = h;
    }

    public void setData(List<ChartBean> d) {
        if (data != null) {
            data.clear();
            data.addAll(d);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mPaddingLeft / 2, mHeight - getPaddingBottom());
        mLinePaint.setStrokeWidth(1);
        int linwWidth = mWidth;
        for (int i = 1; i < present.size(); i++) {
            canvas.drawLine(0, -SizeUtils.px2dp(41) * 1.f * i, linwWidth, -SizeUtils.px2dp(41) * 1.f * i, mLinePaint);
            if (i > 1) {
                mTextPaint.getTextBounds(present.get(i), 0, present.get(i).length(), mTextBound);
                canvas.drawText(present.get(i), 0, -(SizeUtils.px2dp(41) * 1.f * i) + mTextBound.height() + SizeUtils.px2dpH(3), mTextPaint);
            }

        }
        canvas.restore();
    }


    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RectItem item = new RectItem(getContext());
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);//这个数字表示每一个item的宽度
            item.setLayoutParams(layoutParams);
            return new ViewHolder(item);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.item.setText(data.get(position).title, data.get(position).percent);
            if (position==0) {
                holder.item.setBackgroundColor(0x55ff0000);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            RectItem item;

            public ViewHolder(RectItem itemView) {
                super(itemView);
                this.item = itemView;

            }
        }
    }


}