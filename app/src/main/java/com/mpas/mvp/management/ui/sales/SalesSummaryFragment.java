package com.mpas.mvp.management.ui.sales;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentSalesSummaryBinding;
import com.mpas.mvp.management.ManagementActivity;
import com.mpas.mvp.merchant1.model.SalesModel;
import com.mpas.mvp.merchant1.repository.MerchantEntity;
import com.mpas.mvp.merchant1.util.TextConvert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SalesSummaryFragment extends Fragment implements OnChartValueSelectedListener{

    private static final String TAG = SalesSummaryFragment.class.getSimpleName();
    private ManagementActivity managementActivity;
    private SalesViewModel salesViewModel;
    private MerchantViewModel merchantViewModel;
    private FragmentSalesSummaryBinding binding;
    private ArrayAdapter<String> stringArrayAdapter;
    private BarChart barChart;
    private int yesterday;

    public static SalesSummaryFragment newInstance() {
        return new SalesSummaryFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        managementActivity = (ManagementActivity) getActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO 캘린더 recyclerView를 네이버처럼 개선

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sales_summary, container, false);
        merchantViewModel = ManagementActivity.getMerchantViewModel();
        salesViewModel = ManagementActivity.getSalesViewModel();

        salesViewModel.getSalesDb().observe(managementActivity, this::BarChartGraph);
        barChart = binding.barChart;

        merchantViewModel.initialize();
        merchantViewModel.getMerchant_list().observe(managementActivity, arrays -> {

            if (arrays == null || arrays.size() <= 0) {
                //TODO 데이터 없을때 화면 표시
                binding.salesToday.setVisibility(View.GONE);
                binding.salesCardView.setVisibility(View.GONE);
                binding.barChart.setVisibility(View.GONE);
                binding.calendarView.setVisibility(View.GONE);
                binding.listMerchant.setVisibility(View.GONE);
                binding.errorLayout.setVisibility(View.VISIBLE);
            } else {
                stringArrayAdapter = new ArrayAdapter<String>(managementActivity, android.R.layout.simple_expandable_list_item_1, arrays);
                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                binding.listMerchant.setAdapter(stringArrayAdapter);
            }
        });

        merchantViewModel.getTargetMerchant().observe(managementActivity, entity -> {
            if (entity != null) {
                Log.e("targetIn :", entity.toString());
                salesViewModel.getSales("", "", entity.getBusinessNo(), entity.getMerchantNo());
            }
        });

        //######################################

        CalRecyclerAdapter calRecyclerAdapter = new CalRecyclerAdapter(new CalRecyclerAdapter.CalendarListener() {

            @Override
            public void onRefresh(LocalDate localDate, int pos) {
                Log.e("onRefresh", localDate.toString());
                salesViewModel.setSalesDate(localDate);
                LocalDate yesterday = TextConvert.toDay().minusDays(1);
                LocalDate end = localDate;

                if(!yesterday.equals(end)) {

                    String msg = String.format("%s월 %s일 매출",end.getMonthValue(),end.getDayOfMonth());
                    binding.salesSumTitle.setTextSize(20);
                    binding.salesSumTitle.setText(msg);
                    String guide = String.format("* %s/%s/%s일 부터 일주일간 매출을 보여드려요",end.getYear(),end.getMonthValue(),end.getDayOfMonth());
                    binding.salesGuide.setText(guide);
                }else {
                    binding.salesSumTitle.setTextSize(25);
                    binding.salesSumTitle.setText("어제의 매출");
                    binding.salesGuide.setText("* 어제부터 일주일간 매출을 보여드려요");
                }

                LocalDate start = localDate.minusDays(6);
                MerchantEntity target = merchantViewModel.getTargetMerchant().getValue();

                if (target != null) {
                    salesViewModel.getSales(TextConvert.localDateToString(start), TextConvert.localDateToString(end), target.getBusinessNo(), target.getMerchantNo());

                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        binding.calenderRecycler.setLayoutManager(gridLayoutManager);
        binding.calenderRecycler.setAdapter(calRecyclerAdapter);
        binding.calenderRecycler.scrollToPosition(calRecyclerAdapter.getItemCount() - 1);
        //binding.calenderRecycler.setItemViewCacheSize(365); // 설정하지 않으면 12개마다 동일한 동작이 설정됨

        //######################################
        initializeBarChart();
        LocalDate localDate = LocalDate.now();
        String today = String.format("오늘은 %s년 %s월 %s일 이에요", localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        binding.salesToday.setText(today);

        binding.listMerchant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("list set :", adapterView.getSelectedItem().toString());
                merchantViewModel.setTargetMerchant(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.SalesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementActivity.goSales();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 그래프함수
     */

    private void BarChartGraph(List<SalesModel.SaleDB> db) {

        ArrayList<BarEntry> barValues = new ArrayList<BarEntry>();
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<IBarDataSet> iBarDataSet = new ArrayList<IBarDataSet>();
        int i=0;

        for (SalesModel.SaleDB db1 : db) {

            barValues.add(new BarEntry(i, db1.getTramt()));
            labels.add(db1.getTransdatelabel().split(" ")[0]);
            values.add(String.valueOf(db1.getTramt()));

            i++;
        }

        try{
            XAxis xAxis = barChart.getXAxis();
            XValueFormatter custom = new XValueFormatter();
            custom.setDays(labels);
            xAxis.setValueFormatter(custom);
        }catch (Exception e) {
            e.printStackTrace();
        }

        Integer s = Math.round(barValues.get(barValues.size()-1).getY());
        binding.salesSumPrice.setText(TextConvert.toPrice(s));

        int[] MY_COLORS = {
                Color.rgb(153, 153, 204), Color.rgb(153, 153, 204), Color.rgb(102, 102, 153),
                Color.rgb(153, 153, 204), Color.rgb(153, 153, 204), Color.rgb(102, 102, 153),
                Color.rgb(000, 000, 102)
        };

        BarDataSet barDataSet = new BarDataSet(barValues,"어제부터 일주일간");
        barDataSet.setDrawValues(true);
        barDataSet.setValueTextSize(10f);
        iBarDataSet.add(barDataSet);
        BarData data = new BarData(barDataSet);
        barDataSet.setColors(MY_COLORS);
        barDataSet.setStackLabels(labels.toArray(new String[0]));

        //BottomNavigation  클릭시 NullPointException 발생으로 예외처리
        try {

            barChart.setData(data);
            barChart.getBarData().notifyDataChanged();
            barChart.notifyDataSetChanged();
            barChart.invalidate();

            ChartMakerView makerView = new ChartMakerView(managementActivity);
            makerView.setChartView(barChart);
            barChart.setMarker(makerView);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /////chart example
    public void initializeBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.animateY(1000);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setOnChartValueSelectedListener(this);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.setNoDataText("가맹점이 없어요");
        barChart.setDrawBorders(false);
        barChart.setMaxVisibleValueCount(7);

        // chart.setDrawYLabels(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setDrawAxisLine(false);
        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);

        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this repla

/*        // 바차트의 타이틀
        Legend legend = barChart.getLegend();
        // 범례 모양 설정 (default = 정사각형)
        legend.setForm(Legend.LegendForm.LINE);
        // 타이틀 텍스트 사이즈 설정
        legend.setTextSize(20f);
        // 타이틀 텍스트 컬러 설정
        legend.setTextColor( Color.BLACK);
        // 범례 위치 설정
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        // 범례 방향 설정
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        // 차트 내부 범례 위치하게 함 (default = false)
        legend.setDrawInside(false);*/

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        float x = e.getX();
        float y = e.getY();

        Log.e("x", String.valueOf(x));
        Log.e("val", String.valueOf(y));
        //Log.e("getXPx",h.toString());

        Log.e("getDrawX", String.valueOf(h.getDrawX()));
        Log.e("getDrawY", String.valueOf(h.getDrawY()));

    }

    @Override
    public void onNothingSelected() {

    }

    static class XValueFormatter extends ValueFormatter {
        ArrayList<String> days = new ArrayList<>();

        public void setDays(ArrayList<String> days) {
            this.days = days;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            //return super.getAxisLabel(value, axis);
            return days.get((int) value);
        }
    }
}