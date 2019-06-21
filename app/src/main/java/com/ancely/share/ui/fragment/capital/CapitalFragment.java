package com.ancely.share.ui.fragment.capital;

import com.ancely.share.R;
import com.ancely.share.base.BaseFragment;
import com.ancely.share.bean.PieData;
import com.ancely.share.views.chart.PieChart;
import com.ancely.share.views.chart.PieChartView;
import com.ancely.share.views.test.BrokenLineView;

import java.util.ArrayList;

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
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    private PieChart mFragCapitalPchart;

    private BrokenLineView broken_lineview;
    private PieChartView piechartview;

    @Override
    protected void loadData() {
        for (int i = 0; i < 9; i++) {
            PieData pieData = new PieData();
            pieData.setName("区域" + i);
            pieData.setValue((float) i + 1);
            pieData.setColor(mColors[i]);
            mPieDatas.add(pieData);
        }
        mFragCapitalPchart.setStartAngle(0);
        mFragCapitalPchart.setPieData(mPieDatas);
        piechartview.setPieData(mPieDatas);

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(2);
        integers.add(12);
        integers.add(21);
        integers.add(4);
        integers.add(7);
        integers.add(18);
        integers.add(30);
        integers.add(2);
        integers.add(12);
        integers.add(21);
        integers.add(4);
        integers.add(7);
        integers.add(18);
        integers.add(30);
        broken_lineview.setData(integers);
    }

    @Override
    protected void initView() {
        mFragCapitalPchart = (PieChart) findViewById(R.id.frag_capital_pchart);
        broken_lineview = (BrokenLineView) findViewById(R.id.broken_lineview);
        piechartview = (PieChartView) findViewById(R.id.piechartview);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getContentView() {

        return R.layout.fragment_capital;
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }
}
