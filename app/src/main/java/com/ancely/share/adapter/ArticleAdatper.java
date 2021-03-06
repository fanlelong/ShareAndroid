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
 */
public class ArticleAdatper extends RViewAdapter<Article> {

    private AutoLooperView mLooperView;

    public ArticleAdatper(HomeBean datas) {
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
                    holder.setBackgroundRes(R.id.item_frg_home_tv, R.drawable.bg_stroke_r_4_color_e62e3d)
                            .setTextColorRes(R.id.item_frg_home_tv, R.color.color_e62e3d)
                            .setText(R.id.item_frg_home_tv, R.string.top_tip);
                } else {
                    holder.setBackgroundRes(R.id.item_frg_home_tv, R.drawable.bg_stroke_r_4_color_0f66cc)
                            .setTextColorRes(R.id.item_frg_home_tv, R.color.color_0f66cc);
                    if (entry.getTags() != null && entry.getTags().size() > 0) {
                        holder.setText(R.id.item_frg_home_tv, entry.getTags().get(0).getName());
                    } else {
                        holder.setText(R.id.item_frg_home_tv, "新");
                    }

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
        List<HomeBanner> homeBanners = (List<HomeBanner>) o;
        if (homeBanners.size() == 0) {
            return;
        }
        mLooperView = holder.getView(R.id.frag_home_banner);
        mLooperView.setOnBannerItemClick(position1 -> {
            Toast.makeText(ShareApplication.getInstance(), "position " + position1 + "--" + homeBanners.get(position1).getTitle(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(ShareApplication.getInstance(), "position " + position1 + "--" + homeBanners.get(position1).getTitle(), Toast.LENGTH_SHORT).show();
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
        mLooperView.startTurning();
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
