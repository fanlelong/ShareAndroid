package com.ancely.share.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ancely.netan.recycle.base.RViewAdapter;
import com.ancely.netan.recycle.holder.RViewHolder;
import com.ancely.netan.recycle.listener.RViewItem;
import com.ancely.share.R;
import com.ancely.share.ShareApplication;
import com.ancely.share.bean.Article;
import com.ancely.share.bean.HomeBanner;
import com.ancely.share.bean.HomeBean;
import com.ancely.share.views.banner.AutoLooperAdapter;
import com.ancely.share.views.banner.AutoLooperView;
import com.bumptech.glide.Glide;

import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.adapter
 *  @文件名:   HomeAdatper
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/2 3:48 PM
 *  @描述：    TODO
 */
public class HomeAdatper extends RViewAdapter<Article> {

    private AutoLooperView mLooperView;

    public HomeAdatper(HomeBean datas) {
        super(datas.getDatas());
        addItemStyle(new RViewItem<Article>() {
            @Override
            public int getItemLayout() {
                return R.layout.item_frg_home;
            }

            @Override
            public boolean openClick() {
                return true;
            }

            @Override
            public boolean isItemView(Article entry, int position) {
                return true;
            }

            @Override
            public void convert(RViewHolder holder, Article entry, int position) {
                holder.setText(R.id.item_frg_home_auther_tv, entry.getAuthor()).setText(R.id.item_frg_home_tiem_tv, entry.getNiceDate())
                        .setText(R.id.item_frg_home_desc_tv, entry.getTitle())
                        .setText(R.id.item_frg_home_chapter_tv, entry.getSuperChapterName() + "/ " + entry.getChapterName())
                        .setVisible(R.id.item_frg_home_icon_iv, !TextUtils.isEmpty(entry.getEnvelopePic()));
                if (!TextUtils.isEmpty(entry.getEnvelopePic())) {
                    ImageView image = holder.getView(R.id.item_frg_home_icon_iv);
                    Glide.with(image).load(entry.getEnvelopePic()).into(image);
                }
                if (entry.isTop()) {//置顶
                    holder.setVisible(R.id.item_frg_home_tv, true);
                } else if (entry.getType() == 0) {
                    holder.setVisible(R.id.item_frg_home_tv, false);
                }
                if (entry.getTags() != null && entry.getTags().size() > 0) {
                    holder.setText(R.id.item_frg_project_tv, entry.getTags().get(0).getName());
                } else {
                    holder.setText(R.id.item_frg_project_tv, R.string.new_fresh);
                }
                ImageView like = holder.getView(R.id.item_frg_home_like_iv);
                like.setImageResource(entry.isCollect() ? R.drawable.ic_like : R.drawable.ic_like_not);
                like.setOnClickListener(v -> {
                    if (mListener != null) {
                        mListener.onColleclClickListener(entry, entry.getId(), position);
                    }
                });
            }
        });
    }


    @Override
    public void onBindHeaderHolder(RViewHolder holder, int position, int layoutId, Object o) {
        mLooperView = holder.getView(R.id.frag_home_banner);
        List<HomeBanner> homeBanners = (List<HomeBanner>) o;
        if (homeBanners.size() == 0) {
            return;
        }
        mLooperView.setOnBannerItemClick(new AutoLooperView.BannerItemClick() {
            @Override
            public void onBannerItemClick(int position) {
                Toast.makeText(ShareApplication.getInstance(), "position " + position + "--" + homeBanners.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        AutoLooperAdapter<HomeBanner> autoLooperAdapter = new AutoLooperAdapter<HomeBanner>(homeBanners) {
            @Override
            public View creadView(ViewGroup container, int position, HomeBanner entry) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(imageView).load(entry.getImagePath()).into(imageView);
                container.addView(imageView);
                return imageView;
            }
        };
        mLooperView.setDatas(autoLooperAdapter);
        startTruning();
    }

    public void startTruning() {
        if (mLooperView != null) mLooperView.startTurning();
    }

    public void stopTurning() {
        if (mLooperView != null) mLooperView.stopTurning();
    }

    private ColleclClickListener mListener;

    public void setColleclClickListener(ColleclClickListener l) {
        this.mListener = l;
    }

    public interface ColleclClickListener {
        void onColleclClickListener(Article article, int colleckId, int position);
    }
}
