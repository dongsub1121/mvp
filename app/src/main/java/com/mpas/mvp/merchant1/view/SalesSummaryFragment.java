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
        binding.barChart.setTouchEnabled(false); //확대하지못하게 막아버림! 별로 안좋은 기능인 것 같아~
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

            //binding.barChart.setHorizontalScrollBarEnabled(false);



            //binding.barChart.addBar(new BarModel(tag, price, 0xFF56B7F1));
        }
        //binding.barChart.startAnimation();

    }

    /**
     * 그래프함수
     */

    private void BarChartGraph(List<SalesModel.SaleDB> db) {

        BarData barData;
        BarDataSet barDataSet = null;
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> tag = new ArrayList<>();
        String description = null;
        int index = 0;
        Integer max = null;
        String desc = null;

        for(SalesModel.SaleDB data : db) {
            Integer price = data.getTramt();
            if ( price < 100000) {
                price = price/1000;
            } else if ( price < 1000000) {
                price = price/10000;
            } else if ( price > 100000000) {
                price = price/1000000;
            }
            String[] tagArray = data.getTransdatelabel().replace("0","").split(" ");
            entries.add(new BarEntry((Integer) price,index));
            tag.add(tagArray[0]);
            if(max  == null){
                max = data.getTramt();
            } else if (max < data.getTramt()) {
                max = data.getTramt();
            }
            index++;
        }

        if(index == 7) {
            description = "주간 매출 현황";
        } if(index >= 30) {
            description = "월간 매출 현황";
        }

        if(max < 10000 ) {
            desc = "단위: 천원";
        } else if (max > 100000 && max < 1000000 ) {
            desc = "단위: 만원";
        } else if ( max > 1000000) {
            desc = "단위: 백만원";
        }

        binding.barChart.setDescription(desc);
        binding.barChart.setDescriptionTextSize(10);
        binding.barChart.setDescriptionTypeface(Typeface.DEFAULT);
        binding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.barChart.getXAxis().setDrawGridLines(false);
        binding.barChart.setClickable(true);
        binding.barChart.getAxisRight().setEnabled(false);

        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        barDataSet.setValueTextSize(10);
        barDataSet = new BarDataSet(entries, description);
        barData = new BarData(tag,barDataSet);
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        binding.barChart.setData(barData);
        binding.barChart.animateXY(1000, 1000);
        binding.barChart.invalidate();
    }
}