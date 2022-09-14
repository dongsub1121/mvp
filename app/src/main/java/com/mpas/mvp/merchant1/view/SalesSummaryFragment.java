package com.mpas.mvp.merchant1.view;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentSalesSummaryBinding;
import com.mpas.mvp.databinding.SalesFragmentBinding;
import com.mpas.mvp.merchant1.model.SalesModel;
import com.mpas.mvp.merchant1.util.TextConvert;

import org.eazegraph.lib.models.BarModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class SalesSummaryFragment extends Fragment {

    private static final String TAG = SalesSummaryFragment.class.getSimpleName();
    private SalesViewModel mViewModel;
    private FragmentSalesSummaryBinding binding;

    public static SalesSummaryFragment newInstance() {
        return new SalesSummaryFragment();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sales_summary,container,false);
        mViewModel = new ViewModelProvider(this).get(SalesViewModel.class);

        mViewModel.getSalesDb().observe(requireActivity(), this::BarChartGraph);
        binding.barChart.setTouchEnabled(false);
        //binding.barChart.getAxisRight().setAxisMaxValue(80);
        //binding.barChart.getAxisLeft().setAxisMaxValue(80);

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getSales("","");
    }

    private void setBarChart(List<SalesModel.SaleDB> db) {

        for(SalesModel.SaleDB data : db) {
            //String tag = data.getTransdatelabel().replace("0","").replace("","\n");

            String tag = data.getTransdatelabel().replace("0","").split(" ")[0];
            Integer price = data.getTramt()/1000000;

            binding.barChart.setHorizontalScrollBarEnabled(false);
            //binding.barChart.addBar(new BarModel(tag, price, 0xFF56B7F1));
        }
        //binding.barChart.startAnimation();

    }

    /**
     * 그래프함수
     */

    private void BarChartGraph(List<SalesModel.SaleDB> db) {
        ArrayList entry = new ArrayList();
        ArrayList year = new ArrayList();
        int i = 0;

        for(SalesModel.SaleDB data : db) {
            entry.add(new BarEntry(data.getUsnamt(),i));
            year.add(data.getTransdatelabel().split(" ")[0]);
            i++;
        }

        BarDataSet bardataset = new BarDataSet(entry, "주간 매출 현황");
        bardataset.setValueTextSize(10f);

        Legend legend = binding.barChart.getLegend(); //범례 타이틀
        legend.setTextSize(15f);

        binding.barChart.animateY(1000);
        BarData data = new BarData(year, bardataset);      // MPAndroidChart v3.X 오류 발생
        binding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getAxisLeft().setEnabled(false);
        binding.barChart.setDescriptionPosition(1400, 100);
        binding.barChart.setDescription("단위 : 원");
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
        //bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        binding.barChart.setData(data);
    }

/*        BarData barData;
        BarDataSet barDataSet = null;
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> tag = new ArrayList<>();
        String description = null;
        int index = 0;
        String desc = null;

        for(SalesModel.SaleDB data : db) {

            String[] tagArray = data.getTransdatelabel().split(" ");
            tag.add(tagArray[0]);
            Log.e("3333", String.valueOf(data.getUsnamt())); // test tid라서  usnamt로 진행 live시에는 tramt로 진행 해야함
            tag.add(data.getTransdatelabel().replace("0",""));
            entries.add(new BarEntry(data.getUsnamt(),index)); // test tid라서  usnamt로 진행 live시에는 tramt로 진행 해야함

            Log.e("enterti",entries.toString());

            index++;
        }

        if(index >= 7) {
            description = "주간 매출 현황";

        } if(index >= 30) {
            description = "월간 매출 현황";
        }


        binding.barChart.setDescription(desc);
        binding.barChart.setDescriptionTextSize(15);
        binding.barChart.setDescriptionPosition(1400, 100);
        binding.barChart.getXAxis().setDrawGridLines(false);
        //binding.barChart.getXAxis().setSpaceBetweenLabels(1);
        binding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // Tag position

        binding.barChart.setClickable(true);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getAxisLeft().setEnabled(false);
        binding.barChart.setNoDataText("정보를 불러오는 중 입니다.");
        //binding.barChart.setDescriptionPosition(500,0);
        barDataSet = new BarDataSet(entries, description);
        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        barDataSet.setValueTextSize(10);
        barDataSet.setLabel(description);
        barDataSet.setBarSpacePercent(30);

        barData = new BarData(tag,barDataSet);
        //barData.setValueTextColors(); // 차트 컬러 리스트

        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        binding.barChart.setData(barData);
        binding.barChart.animateXY(1000, 1000);
        binding.barChart.invalidate();
    }*/
}