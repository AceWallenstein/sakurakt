package com.blankspace.sakura.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.blankspace.sakura.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {
    private PieChart mChart;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mChart = (PieChart) findViewById(R.id.pieChart);
        lineChart = (LineChart) findViewById(R.id.line_chart);
        lineChart.setDrawGridBackground(false);//是否绘制网格背景


        PieData mPieData = getPieData(4, 100);
        showChart(mChart, mPieData);

        List<Entry> entries = new ArrayList<Entry>();

        for (int i = 1; i < 20; i++) {
            // turn your data into Entry objects
            // 图形横纵坐标默认为float形式，如果想展示文字形式，需要自定义适配器。后边会讲，这个地方传进去的X轴Y轴值都是float类型
            entries.add(new Entry(i, (float) Math.random() * 10));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // 图表绑定数据，设置图表折现备注
        dataSet.setColor(Color.parseColor("#00ff00")); // 设置折线图颜色
        dataSet.setValueTextColor(Color.parseColor("#000000")); // 设置数据值的颜色

        Description description = lineChart.getDescription();
        description.setText("typeName" + "历史数据折线图"); // 设置右下角备注

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData); // 图表绑定数据值
        lineChart.invalidate(); // 刷新图表

    }

    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆
        Description description = new Description();
        description.setText("测试饼状图");
        pieChart.setDescription(description);
        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90); // 初始旋转角度
        pieChart.setRotationEnabled(true); // 可以手动旋转
        pieChart.setUsePercentValues(true);  //显示成百分比
        pieChart.setCenterText("测试");  //饼状图中间的文字
        pieChart.setCenterTextSize(18);
        //设置数据
        pieChart.setData(pieData);
        Legend mLegend = pieChart.getLegend();  //设置比例图
//        mLegend.se(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
        //      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        pieChart.animateXY(1000, 1000);  //设置动画
    }

    /**
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        for (int i = 0; i < count; i++) {
            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        }

        ArrayList<PieEntry> yValues = new ArrayList();  //yVals用来表示封装每个饼块的实际数据
        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = 14;
        float quarterly2 = 14;
        float quarterly3 = 34;
        float quarterly4 = 38;
        yValues.add(new PieEntry(quarterly1, 0));
        yValues.add(new PieEntry(quarterly2, 1));
        yValues.add(new PieEntry(quarterly3, 2));
        yValues.add(new PieEntry(quarterly4, 3));
        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "Quarterly Revenue 2014"/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        ArrayList<Integer> colors = new ArrayList<Integer>();
        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        pieDataSet.setColors(colors);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        PieData pieData = new PieData(pieDataSet);
        return pieData;
    }

}