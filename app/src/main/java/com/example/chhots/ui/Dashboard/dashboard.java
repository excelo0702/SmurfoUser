package com.example.chhots.ui.Dashboard;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.CircularGauge;
import com.anychart.charts.Pie;
import com.anychart.core.axes.Circular;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.enums.Anchor;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.graphics.vector.text.VAlign;
import com.example.chhots.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class dashboard extends Fragment {


    public dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        AnyChartView anyChartView = (AnyChartView)view. findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar_chart));

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.data(new SingleValueDataSet(new String[]{"23","35","67", "93", "56", "100"}));

        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0d, 0d, 0d, 0d)
                .margin(0, 0, 40d, 100d);
        circularGauge.startAngle(0d);
        circularGauge.sweepAngle(470d);

        Circular xAxis = circularGauge.axis(0)
                .radius(60d)
                .width(1d)
                .fill((Fill) null);
        xAxis.scale()
                .minimum(0d)
                .maximum(70d);
        xAxis.ticks("{ interval: 1 }")
                .minorTicks("{ interval: 1 }");
        xAxis.labels().enabled(false);
        xAxis.ticks().enabled(false);
        xAxis.minorTicks().enabled(false);

        circularGauge.label(0d)
                .text("Temazepam, <span style=\"\">32%</span>")
                .useHtml(true);

        Bar bar0 = circularGauge.bar(0d);
        bar0.dataIndex(0d);
        bar0.radius(10d);
        bar0.width(5d);
        bar0.fill(new SolidFill("#64b5f6", 1d));
        bar0.stroke(null);
        bar0.zIndex(5d);
        Bar bar100 = circularGauge.bar(100d);
        bar100.dataIndex(1d);
        bar100.radius(10d);
        bar100.width(5d);
        bar100.fill(new SolidFill("#F5F4F4", 1d));
        bar100.stroke("1 #e5e4e4");
        bar100.zIndex(4d);

        anyChartView.setChart(circularGauge);



        return view;
    }

}
