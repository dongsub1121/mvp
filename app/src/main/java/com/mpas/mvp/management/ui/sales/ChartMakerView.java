package com.mpas.mvp.management.ui.sales;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.mpas.mvp.R;
import com.mpas.mvp.merchant1.util.TextConvert;

import java.text.DecimalFormat;

public class ChartMakerView extends MarkerView{

    private final TextView tvContent;



    public ChartMakerView(Context context) {
        super(context, R.layout.chart_custom_maker);

        tvContent = findViewById(R.id.tooltip);

    }

    @Override
    public void setOffset(MPPointF offset) {
        super.setOffset(offset);
    }

    @Override
    public void setOffset(float offsetX, float offsetY) {
        super.setOffset(offsetX, offsetY);
    }

    @Override
    public MPPointF getOffset() {
        //return super.getOffset();
        return new MPPointF(getWidth(), -(getHeight()));
    }

    @Override
    public void setChartView(Chart chart) {
        super.setChartView(chart);
    }

    @Override
    public Chart getChartView() {
        return super.getChartView();
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        return super.getOffsetForDrawingAtPoint(posX, posY);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(String.format(" 총: %s", TextConvert.toPrice(Math.round(e.getY()))));
        super.refreshContent(e, highlight);

    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        super.draw(canvas, posX, posY);
    }
}
