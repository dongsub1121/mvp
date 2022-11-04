package com.mpas.mvp.merchant1.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentSalesSummaryBinding;
import com.mpas.mvp.merchant1.model.SalesModel;
import com.mpas.mvp.merchant1.repository.MerchantFactory;
import com.mpas.mvp.merchant1.util.TextConvert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SalesSummaryFragment extends Fragment {

    private static final String TAG = SalesSummaryFragment.class.getSimpleName();
    private SalesViewModel salesViewModel;
    private MerchantViewModel merchantViewModel;
    private FragmentSalesSummaryBinding binding;
    private ArrayAdapter<String> stringArrayAdapter;
    private int yesterday;

    public static SalesSummaryFragment newInstance() {
        return new SalesSummaryFragment();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sales_summary,container,false);
        salesViewModel = new ViewModelProvider(this).get(SalesViewModel.class);
        merchantViewModel = ManagementActivity.getViewModel();

        salesViewModel.getSalesDb().observe(requireActivity(), this::BarChartGraph);
        binding.barChart.setTouchEnabled(false);

        merchantViewModel.getMerchant_list().observe(requireActivity(),arrays ->{
            stringArrayAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_expandable_list_item_1, arrays);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
            binding.listMerchant.setAdapter(stringArrayAdapter);
        });

        merchantViewModel.getMerchant().observe(requireActivity(),entity->{
            salesViewModel.getSales("","",entity.getBusinessNo(),entity.getMerchantNo());
        });

        //######################################
        InfoAdapter infoAdapter = new InfoAdapter();
        CalRecyclerAdapter calRecyclerAdapter = new CalRecyclerAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(),1,GridLayoutManager.HORIZONTAL,false);
        binding.calenderRecycler.setLayoutManager(gridLayoutManager);
        binding.calenderRecycler.setAdapter(calRecyclerAdapter);
        binding.calenderRecycler.scrollToPosition(infoAdapter.getItemCount()-1);
        //######################################

        LocalDate localDate = LocalDate.now();
        String today = String.format("오늘은 %s년 %s월 %s일 이에요", localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth());
        binding.salesToday.setText(today);

        binding.listMerchant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                merchantViewModel.SetMerchant(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagementActivity.goFragment(1);
            }
        });

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        merchantViewModel.getMerchantList();
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
            entry.add(new BarEntry(data.getTramt(),i));
            year.add(data.getTransdatelabel().split(" ")[0]);
            yesterday =  data.getTramt();
            Log.e("yesterday", String.valueOf(yesterday));
            i++;
        }

        binding.salesSumPrice.setText(TextConvert.toPrice(yesterday));

        //Color Create
       int[] MPAS_COLORS =  {
               Color.rgb(204, 204, 255),Color.rgb(204, 204, 255),Color.rgb(204, 204, 255),
               Color.rgb(204, 204, 255),Color.rgb(204, 204, 255),Color.rgb(204, 204, 255),
               Color.rgb(000, 000, 102)
        };

        int[] MPAS_COLORS2 =  {
                Color.rgb(153, 153, 230),Color.rgb(153, 153, 230),Color.rgb(153, 153, 204),
                Color.rgb(153, 153, 230),Color.rgb(153, 153, 230),Color.rgb(153, 153, 204),
                Color.rgb(000, 000, 102)
        };

        int[] MY_COLORS =  {
                Color.rgb(153, 153, 204), Color.rgb(153, 153, 204),Color.rgb(102, 102, 153),
                Color.rgb(153, 153, 204), Color.rgb(153, 153, 204),Color.rgb(102, 102, 153),
                Color.rgb(000, 000, 102)
        };

        BarDataSet bardataset = new BarDataSet(entry, "");
        bardataset.setValueTextSize(10f);

        Legend legend = binding.barChart.getLegend(); //범례 타이틀
        legend.setTextSize(15f);

        binding.barChart.animateY(1000);
        BarData data = new BarData(year, bardataset);      // MPAndroidChart v3.X 오류 발생
        binding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.barChart.getXAxis().setDrawGridLines(false);
        binding.barChart.getXAxis().setDrawLabels(true); // 차트 라벨 설정
        binding.barChart.setDrawBarShadow(false); // 차트 객체 백그라운드
        binding.barChart.setClickable(true);
        binding.barChart.setDrawHighlightArrow(true);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getAxisLeft().setEnabled(false);
        binding.barChart.setDrawValueAboveBar(true);

        binding.barChart.setDescriptionPosition(1350, 100);
        binding.barChart.setDescription("단위 : 원");
        bardataset.setBarSpacePercent(20);
        bardataset.setColors(MY_COLORS);  //bardataset.setColors(ColorTemplate.PASTEL_COLORS);
        binding.barChart.setData(data);
        binding.barChart.invalidate();
    }
}