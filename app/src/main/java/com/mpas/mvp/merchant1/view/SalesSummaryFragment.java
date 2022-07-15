package com.mpas.mvp.merchant1.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentSalesSummaryBinding;
import com.mpas.mvp.databinding.SalesFragmentBinding;
import com.mpas.mvp.merchant1.model.SalesModel;
import com.mpas.mvp.merchant1.util.TextConvert;

import org.eazegraph.lib.models.BarModel;

import java.time.LocalDate;
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
        mViewModel.getSales("","");

        mViewModel.getSalesDb().observe(requireActivity(), this::setBarChart);
        return binding.getRoot();
    }

    private void setBarChart(List<SalesModel.SaleDB> db) {

        for(SalesModel.SaleDB data : db) {
            String tag = data.getTransdatelabel().replace("0","");
            Integer price = data.getTramt()/10000;
            binding.barChart.addBar(new BarModel(tag, price, 0xFF56B7F1));
        }
        binding.barChart.startAnimation();

    }

}