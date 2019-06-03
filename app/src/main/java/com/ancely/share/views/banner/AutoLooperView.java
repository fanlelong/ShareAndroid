package com.ancely.share.views.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ancely.share.R;
import com.ancely.share.utils.DrawableUtils;
import com.ancely.share.utils.SizeUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Objects;

/*
 *  @项目名：  AncelyView
 *  @包名：    simcpux.sourceforge.net.ancelyview.view
 *  @文件名:   AutoLooperView
 *  @创建者:   fan
 *  @创建时间:  2016/9/20 0:07
 *  @描述：    自定义自动轮播View
 */
public class AutoLooperView extends RelativeLayout implements ViewPager.OnPageChangeListener, CustomViewpager.OnItemClickListener {

    private CustomViewpager mViewPager;
    private LinearLayout mDotContainer;
    private Context mContext;
    private ViewPagerScroller mScroll;

    private AdSwitchTask adSwitchTask;
    private boolean turning;//是否开启了翻页
    private boolean canTurn;//是否在自动滚动

    private long autoTurningTime;
    private int mItemSize;
    private GradientDrawable mSelectPoint;
    private GradientDrawable mNormalPoint;

    private int selectColor = R.color.color_0f66cc;
    private int normalColor = R.color.color_550f66cc;

    public AutoLooperView(Context context) {
        this(context, null);
    }

    public AutoLooperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLooperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoLooperView);
        selectColor = a.getResourceId(R.styleable.AutoLooperView_dotSelect, selectColor);
        normalColor = a.getResourceId(R.styleable.AutoLooperView_dotSelect, normalColor);
        autoTurningTime = a.getInt(R.styleable.AutoLooperView_scrollSpeed, 4000);
        a.recycle();

        mViewPager = new CustomViewpager(context);
        LayoutParams viewPagerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mViewPager, viewPagerParams);

        mDotContainer = new LinearLayout(context);
        mDotContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams dotContainerPagerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.px2dpH(60));
        dotContainerPagerParams.addRule(ALIGN_PARENT_BOTTOM);
        mDotContainer.setGravity(Gravity.CENTER);
        addView(mDotContainer, dotContainerPagerParams);

        creatDotDrawable(context);
        mContext = context;
        adSwitchTask = new AdSwitchTask(this);
        initViewPagerScroll();
        initListener();
    }

    private void creatDotDrawable(Context context) {
        mSelectPoint = DrawableUtils.creatDrable(context, selectColor, 20);
        mNormalPoint = DrawableUtils.creatDrable(context, normalColor, 20);

    }

    public void setDatas(AutoLooperAdapter adapter) {
        mItemSize = adapter.getItemSize();
        initPointView();
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
    }

    /**
     * 通知数据变化
     * 如果只是增加数据建议使用 notifyDataSetAdd()
     */
    public void notifyDataSetChanged() {
        Objects.requireNonNull(mViewPager.getAdapter()).notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        position = getRealPositon(position);
        if (listener != null) {
            listener.onBannerItemClick(position);
        }
    }

    private BannerItemClick listener;

    public void setOnBannerItemClick(BannerItemClick l) {
        listener = l;
    }

    public interface BannerItemClick {
        void onBannerItemClick(int position);
    }

    static class AdSwitchTask implements Runnable {

        private final WeakReference<AutoLooperView> reference;

        AdSwitchTask(AutoLooperView convenientBanner) {
            this.reference = new WeakReference<>(convenientBanner);
        }

        @Override
        public void run() {
            AutoLooperView convenientBanner = reference.get();
            if (convenientBanner != null) {
                if (convenientBanner.mViewPager != null && convenientBanner.turning) {
                    int position = convenientBanner.mViewPager.getCurrentItem();
                    convenientBanner.mViewPager.setCurrentItem(position + 1);
                    convenientBanner.postDelayed(convenientBanner.adSwitchTask, convenientBanner.autoTurningTime);
                }
            }
        }
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroll = new ViewPagerScroller(mViewPager.getContext());
            mScroller.set(mViewPager, mScroll);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setCanTurn(boolean canTurn) {
        this.canTurn = canTurn;
    }

    /**
     * 设置滚动时长
     *
     * @param scrollSpeed 时间
     */
    public void setScrollSpeed(int scrollSpeed) {
        mScroll.setScrollDuration(scrollSpeed);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOnItemClickListener(this);
    }

    private void initPointView() {
        //动态初始化点的个数
        mDotContainer.removeAllViews();
        for (int i = 0; i < mItemSize; i++) {
            View dot = new View(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(SizeUtils.px2dp(5), SizeUtils.px2dp(5));
            params.leftMargin = SizeUtils.px2dp(4);
            params.bottomMargin = SizeUtils.px2dp(4);
            dot.setLayoutParams(params);
            if (i == 0) {
                dot.setBackground(mSelectPoint);
            } else {
                dot.setBackground(mNormalPoint);
            }
            mDotContainer.addView(dot);
        }
    }


    /**
     * 设置自动翻页的时长
     */
    public void setAutoTurningTime(long autoTurningTime) {
        this.autoTurningTime = autoTurningTime;
    }

    /***
     * 开始翻页
     */
    public void startTurning() {
        //如果是正在翻页的话先停掉
        if (turning) {
            stopTurning();
        }
        //设置可以翻页并开启翻页
        canTurn = true;
        turning = true;
        postDelayed(adSwitchTask, autoTurningTime);
    }

    public void stopTurning() {
        turning = false;
        removeCallbacks(adSwitchTask);
    }

    //触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn) startTurning();
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn) stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    //前一次被选中的条目
    int mLastSelectPositon = 0;

    @Override
    public void onPageSelected(int position) {
        position = getRealPositon(position);
        if (position == mLastSelectPositon) {
            return;
        }
        View dot = mDotContainer.getChildAt(position);

        dot.setBackground(mSelectPoint);
        View predot = mDotContainer.getChildAt(mLastSelectPositon);
        predot.setBackground(mNormalPoint);

        mLastSelectPositon = position;
    }

    /**
     * 页面滚动状态发生变化的回调
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        int position = mViewPager.getCurrentItem();
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (position == 0 || position == mItemSize + 1) {
                position = getRealPositon(position);
                position = position + 1;
                mViewPager.setCurrentItem(position, false);
            }
        }
    }


    /**
     * 获取真实的positon
     */
    public int getRealPositon(int position) {

        int realPosition = (position - 1) % mItemSize;
        if (realPosition < 0) {
            realPosition += mItemSize;
        }
        return realPosition;
    }
}
