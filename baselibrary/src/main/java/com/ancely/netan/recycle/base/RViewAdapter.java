package com.ancely.netan.recycle.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.ancely.netan.recycle.holder.RViewHolder;
import com.ancely.netan.recycle.listener.ItemListener;
import com.ancely.netan.recycle.listener.RViewItem;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.commoentmodule.recycle.adapter
 *  @文件名:   RViewAdapter
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/21 2:03 PM
 *  @描述:    多样式item样式适配器
 */
public class RViewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {
    private RViewItemManager<T> itemStyle;//item类管理器
    private ItemListener<T> itemListener;//item点击监听
    private List<T> datas;//数据源

    //单样式
    public RViewAdapter(List<T> datas) {
        if (datas == null) datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RViewItemManager<>();
    }

    //多样式
    public RViewAdapter(List<T> datas, RViewItem<T> item) {
        if (datas == null) datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RViewItemManager<>();
        //将item类型加入到管理器
        addItemStyle(item);
    }

    //增加一种新的item样式
    private void addItemStyle(RViewItem<T> item) {
        itemStyle.addSytle(item);
    }

    //会根据不同的类型Item,会创建不同的ViewHolder
    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RViewItem item = itemStyle.getRViewItem(viewType);
        int layoutId = item.getItemLayout();
        RViewHolder holder = RViewHolder.createViewHolder(viewGroup.getContext(), viewGroup, layoutId);
        if (item.openClick()) setListener(holder);
        return holder;
    }

    private void setListener(final RViewHolder holder) {
        holder.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null) {
                    int position = holder.getAdapterPosition();
                    itemListener.onItemClick(v, datas.get(position), position);
                }
            }
        });
        holder.getContentView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemListener != null) {
                    int position = holder.getAdapterPosition();
                    return itemListener.onItemLongClick(v, datas.get(position), position);
                }
                return false;
            }
        });

    }


    //数据绑定
    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        convert(holder, datas.get(position), position);
    }

    private void convert(RViewHolder holder, T t, int position) {
        itemStyle.convert(holder, t, position);
    }

    //根据position获取当前的样式
    @Override
    public int getItemCount() {
        return this.datas == null ? 0 : this.datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (hasMultistyle()) return itemStyle.getItemViewType(datas.get(position), position);
        return super.getItemViewType(position);
    }

    //是否有多样式的RViewItem
    private boolean hasMultistyle() {
        return itemStyle.getItemViewStyleCount() > 0;
    }

    //添加数据
    public void addDatas(List<T> datas) {
        if (datas == null) return;
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    //更新数据
    public void updataDatas(List<T> datas) {
        if (datas == null) return;
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener<T> itemListener) {
        this.itemListener = itemListener;
    }
}
