package com.ancely.netan.recycle.base;

import android.support.v4.util.SparseArrayCompat;
import com.ancely.netan.recycle.holder.RViewHolder;
import com.ancely.netan.recycle.listener.RViewItem;

/*
 *  @项目名：  ModlerApp
 *  @包名：    luckly.ancely.com.commoentmodule.recycle.base
 *  @文件名:   RViewItemManager
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/21 2:04 PM
 *  @描述：    条目管理类 配合RViewAdapter来处理实现OOP思想
 */
public class RViewItemManager<T> {
    //styles 每添加一个证明就多一种布局就多一个viewType
    private SparseArrayCompat<RViewItem<T>> styles = new SparseArrayCompat<>();//key: viewType; value:RViewItem;

    public void addSytle(RViewItem<T> item) {
        if (item != null) {
            styles.put(styles.size(), item);
        }

    }

    //有没有多样式
    public int getItemViewStyleCount() {
        return styles.size();
    }

    //根据显示的ViewType返回RViewItem(集合的value)
    public RViewItem getRViewItem(int viewType) {
        return styles.get(viewType);
    }

    //根据数据源和数据返回某个item类型的viewType
    public int getItemViewType(T entity, int positon) {
        for (int i = styles.size() - 1; i >= 0; i--) {//防止数据被移除 数据不存在
            RViewItem<T> item = styles.valueAt(i);
            if (item.isItemView(entity, positon)) {//是否为当前样式
                return styles.keyAt(i);//获取集合key
            }
        }
        throw new IllegalArgumentException("no itemView");
    }

    //试图和数据源绑定
    public void convert(RViewHolder holder, T entity, int position) {
        for (int i = 0; i < styles.size(); i++) {

            RViewItem<T> viewItem = styles.valueAt(i);
            if (viewItem.isItemView(entity, position)) {
                viewItem.convert(holder, entity, position);
            }
        }
    }
}
