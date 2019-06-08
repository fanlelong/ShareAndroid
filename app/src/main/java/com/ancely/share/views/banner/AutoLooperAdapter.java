package com.ancely.share.views.banner;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class AutoLooperAdapter<T> extends PagerAdapter {
    private List<T> datas = new ArrayList<>();

    protected AutoLooperAdapter(List<T> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.datas.clear();
        this.datas.addAll(datas);
    }

    public void setDatas(List<T> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return datas.size() + 2;
    }

    public int getItemSize() {
        return datas.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        position = getRealPositon(position);
        return creadView(container, position, datas.get(position));
    }

    public abstract View creadView(ViewGroup container, int position, T entry);

    /**
     * 获取真实的positon
     */
    private int getRealPositon(int position) {

        int realPosition = (position - 1) % datas.size();
        if (realPosition < 0) {
            realPosition += datas.size();
        }
        return realPosition;
    }
}