package com.ancely.netan.recycle.base;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
 *  @描述:    多样式item样式适配器 可以添加头部和脚步
 */
public class RViewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 1000000;//headerview的viewtype基准值
    private static final int BASE_ITEM_TYPE_FOOTER = 2000000;//footerView的ViewType基准值
    private RViewItemManager<T> itemStyle;//item类管理器
    private ItemListener<T> itemListener;//item点击监听
    private List<T> datas;//数据源
    private SparseArrayCompat<SparseArrayCompat> mHeaderDatas = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();//存放FooterViews,key是viewType

    //单样式
    public RViewAdapter(List<T> datas) {
        if (datas == null) datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RViewItemManager<>();
    }

    public List<T> getDatas() {
        return datas;
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
    protected void addItemStyle(RViewItem<T> item) {
        itemStyle.addSytle(item);
    }


    private void setListener(final RViewHolder holder) {
        holder.getContentView().setOnClickListener(v -> {
            if (itemListener != null) {
                int position = holder.getAdapterPosition();
                itemListener.onItemClick(v, datas.get(position - mHeaderDatas.size()), position);
            }
        });
        holder.getContentView().setOnLongClickListener(v -> {
            if (itemListener != null) {
                int position = holder.getAdapterPosition();
                return itemListener.onItemLongClick(v, datas.get(position - mHeaderDatas.size()), position);
            }
            return false;
        });

    }

    //会根据不同的类型Item,会创建不同的ViewHolder
    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (mHeaderDatas.get(viewType) != null) {//不为空，说明是headerview
            return RViewHolder.createViewHolder(viewGroup.getContext(), viewGroup, mHeaderDatas.get(viewType).keyAt(0));
        } else if (mFooterViews.get(viewType) != null) {
            return RViewHolder.createViewHolder(mFooterViews.get(viewType));
        }
        RViewItem item = itemStyle.getRViewItem(viewType);
        int layoutId = item.getItemLayout();
        RViewHolder holder = RViewHolder.createViewHolder(viewGroup.getContext(), viewGroup, layoutId);
        if (item.openClick()) setListener(holder);
        return holder;
    }

    //数据绑定
    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            convert(holder, position);
            return;
        } else if (isFooterViewPos(position)) {
            return;
        }
        convert(holder, datas.get(position - mHeaderDatas.size()), position);
    }

    private void convert(RViewHolder holder, T t, int position) {

        itemStyle.convert(holder, t, position);
    }

    private void convert(RViewHolder holder, int position) {
        SparseArrayCompat sparseArrayCompat = mHeaderDatas.get(getItemViewType(position));
        if (sparseArrayCompat != null && sparseArrayCompat.size() > 0) {
            int layoutId = sparseArrayCompat.keyAt(0);
            onBindHeaderHolder(holder, position, layoutId, sparseArrayCompat.get(layoutId));
        }
    }

    public void onBindHeaderHolder(RViewHolder holder, int position, int layoutId, Object o) {
    }

    //根据position获取当前的样式
    @Override
    public int getItemCount() {
        return this.datas.size() + getHeaderViewCount() + getFooterViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderDatas.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - mFooterViews.size() - datas.size());
        }
        if (hasMultistyle())
            return itemStyle.getItemViewType(datas.get(position - mHeaderDatas.size()), position);
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


    public void addRangeDatas(List<T> datas) {
        this.datas.addAll(datas);
        notifyItemRangeChanged(this.datas.size() + mHeaderDatas.size(), datas.size());
    }

    //更新数据
    public void updataDatas(List<T> datas) {
        if (datas == null) return;
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }


    //更新数据
    public void updataRangeDatas(List<T> datas) {
        if (datas == null) return;
        this.datas.clear();
        this.datas.addAll(datas);
        notifyItemRangeChanged(mHeaderDatas.size(), this.datas.size());
    }


    public void setItemListener(ItemListener<T> itemListener) {
        this.itemListener = itemListener;
    }


    public int getHeaderViewCount() {
        return mHeaderDatas.size();
    }

    /**
     * 传入position 判断是否是headerview
     */
    public boolean isHeaderViewPos(int position) {// 举例， 2 个头，pos 0 1，true， 2+ false
        return getHeaderViewCount() > position;
    }

    /**
     * 传入postion判断是否是footerview
     *
     * @param position
     * @return
     */
    public boolean isFooterViewPos(int position) {//举例， 2个头，2个inner，pos 0 1 2 3 ,false,4+true
        return position >= datas.size() + getHeaderViewCount();
    }

    public int getFooterViewCount() {
        return mFooterViews.size();
    }

    /**
     * 添加HeaderView
     *
     * @param layoutId headerView 的LayoutId
     * @param data     headerView 的data(可能多种不同类型的header 只能用Object了)
     */
    public void addHeaderView(int layoutId, Object data) {
        //mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, v);
        SparseArrayCompat headerContainer = new SparseArrayCompat();
        headerContainer.put(layoutId, data);
        mHeaderDatas.put(mHeaderDatas.size() + BASE_ITEM_TYPE_HEADER, headerContainer);
    }

    /*
     * 添加FooterView
     */
    public void addFooterView(View v) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, v);
    }

    /**
     * 清空HeaderView数据
     */
    public void clearHeaderView() {
        mHeaderDatas.clear();
    }

    public void clearFooterView() {
        mFooterViews.clear();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        //为了兼容GridLayout
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderDatas.get(viewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    } else if (mFooterViews.get(viewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null)
                        return spanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {

                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }


}
