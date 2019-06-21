package com.ancely.share.views.test;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xk on 2016/11/9 22:14.
 */

public class BrokenLineView extends RecyclerView {

    private int maxValue;
    private int minValue;
    private List<Integer> data = new ArrayList<>();
    private Adapter adapter;
    private Paint mLinePaint;
    private int mWidth;
    private int mHeight;

    public BrokenLineView(Context context) {
        this(context, null);

    }

    public BrokenLineView(@NonNull Context context, @Nullable AttributeSet attrs) {
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


        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new Adapter();
        setAdapter(adapter);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int paddingLeft = getPaddingLeft();
        mWidth = w - paddingLeft - getPaddingRight();
        mHeight = h - getPaddingBottom() - getPaddingTop();
    }

    public void setData(List<Integer> d) {
        if (data != null) {
            data.clear();
            data.addAll(d);
            Collections.sort(d);
            minValue = d.get(0);
            maxValue = d.get(d.size() - 1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, mHeight);
        canvas.drawPoint(0, 0, mLinePaint);

        mLinePaint.setStrokeWidth(1);
        canvas.drawLine(0, 0, mWidth, 0, mLinePaint);

        canvas.drawLine(0, 0, 0, -mHeight * 0.8f, mLinePaint);
//
//        canvas.drawLine(mWidth - 120, 0, (mWidth - 120) * 0.98f, (mWidth - 120) * 0.02f, mLinePaint);
//        canvas.drawLine(mWidth - 120, 0, (mWidth - 120) * 0.98f, -(mWidth - 120) * 0.02f, mLinePaint);
////
//        canvas.drawLine(0, -mHeight * 0.8f, (-mHeight * 0.8f) * 0.02f, (-mHeight * 0.8f) * 0.98f, mLinePaint);
//        canvas.drawLine(0, -mHeight * 0.8f, -(-mHeight * 0.8f) * 0.02f, (-mHeight * 0.8f) * 0.98f, mLinePaint);
        int linwWidth = mWidth;
        int charHeight = (int) (-mHeight * 0.8f);
        int num = linwWidth / 240;
        int heightNum = -charHeight / 120;
        for (int i = 1; i < num; i++) {
            canvas.drawLine(linwWidth * 1.f * i / num, 0, linwWidth * 1.f * i / num, -10, mLinePaint);

        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{8, 4, 16, 4}, 6));
        for (int i = 0; i < heightNum; i++) {
            canvas.drawLine(0, charHeight * 1.f * i / heightNum, linwWidth, charHeight * 1.f * i / heightNum, mLinePaint);
        }
        mLinePaint.setPathEffect(null);
        canvas.restore();
        super.onDraw(canvas);
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Item item = new Item(getContext());
            item.setMinValue(minValue);
            item.setMaxValue(maxValue);
            LayoutParams layoutParams = new LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT);//这个数字表示每一个item的宽度
            item.setLayoutParams(layoutParams);
            return new ViewHolder(item);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position == 0) {
                holder.item.setDrawLeftLine(false);
            } else {
                holder.item.setDrawLeftLine(true);
                holder.item.setlastValue((data.get(position - 1)));
            }
            holder.item.setCurrentValue((data.get(position)));


            if (position == data.size() - 1) {
                holder.item.setDrawRightLine(false);
            } else {
                holder.item.setDrawRightLine(true);
                holder.item.setNextValue((data.get(position + 1)));
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            Item item;

            public ViewHolder(View itemView) {
                super(itemView);
                this.item = (Item) itemView;
            }
        }
    }


}