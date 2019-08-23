package com.ancely.share.ui.fragment.capital;

import com.ancely.share.R;
import com.ancely.share.base.BaseFragment;
import com.ancely.share.bean.PieData;
import com.ancely.share.views.chart.PieChartView;
import com.ancely.share.views.test.BrokenLineView;
import com.ancely.share.views.test.ChartBean;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.fragment.capital
 *  @文件名:   CapitalFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/6/18 11:45 AM
 *  @描述：    TODO
 */
public class CapitalFragment extends BaseFragment {
    private ArrayList<PieData> mPieDatas = new ArrayList<>();
    // 颜色表
    private int[] mColors = {0xFF52c6f6, 0xFFB4B4B4, 0xFF977DFF, 0xFF9eea5e, 0xFFffa8a8, 0xFFffdb4c};
    private int[] mColors1 = {0xFF2995ea, 0xFF000000, 0xFF7561F3, 0xFF81cd42, 0xFFf34747, 0xFFffb631};

    private BrokenLineView broken_lineview;
    private PieChartView piechartview;

    @Override
    protected void loadData() {


        for (int i = 0; i < 1; i++) {
            List<Integer> colors = new ArrayList<>();
            colors.add(mColors[i]);
            colors.add(mColors1[i]);
            PieData pieData = new PieData();
            pieData.setName("区域" + i);
            pieData.setValue((float) i + 1);
            pieData.setColor(mColors[i]);
            pieData.setColors(colors);
            mPieDatas.add(pieData);
        }
        piechartview.setPieData(mPieDatas);

        ArrayList<ChartBean> chartBeans = new ArrayList<>();
        chartBeans.add(new ChartBean("籓edis哦",0.6f));
//        chartBeans.add(new ChartBean("籓eeis哦",0.6f));
        chartBeans.add(new ChartBean("籓edeis哦",0.4f));
//        chartBeans.add(new ChartBean("籓edeis哦",0.6f));
        chartBeans.add(new ChartBean("籓edes哦",0.9f));
//        chartBeans.add(new ChartBean("籓edis哦需要",0.8f));
        chartBeans.add(new ChartBean("籓edeis哦",1.0f));
//        chartBeans.add(new ChartBean("籓edeis哦在",0.6f));
//        chartBeans.add(new ChartBean("籓edes哦fd",0.1f));
//        chartBeans.add(new ChartBean("籓edeis哦d",0.32f));
//        chartBeans.add(new ChartBean("籓edes哦地",0.66f));
//        chartBeans.add(new ChartBean("籓deis哦d",0.68f));
//        broken_lineview.setData(integers);
        broken_lineview.setData(chartBeans);
    }

    @Override
    protected void initView() {
        broken_lineview = (BrokenLineView) findViewById(R.id.broken_lineview);
        piechartview = (PieChartView) findViewById(R.id.piechartview);
//        mRectitem = (RectItem) findViewById(R.id.rectitem);
//        mRectitem.setText("天天天天天",0.5f);
//        mRectitem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v instanceof RectItem) {
//                    ((RectItem) v).setText("天天天",0.8f);
//                }
//            }
//        });


    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getContentView() {

        return R.layout.fragment_capital;
    }

    @Override
    protected Class initClazz() {
        return null;
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }
}
