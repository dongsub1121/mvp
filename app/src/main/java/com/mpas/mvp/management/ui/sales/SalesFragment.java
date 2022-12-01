package com.mpas.mvp.management.ui.sales;

import static com.mpas.mvp.merchant1.util.TextConvert.toPrice;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.SalesFragmentBinding;
import com.mpas.mvp.management.ManagementActivity;
import com.mpas.mvp.merchant1.model.SalesDetailModel;
import com.mpas.mvp.merchant1.util.TextConvert;
import com.mpas.mvp.merchant1.view.CalRecyclerAdapter;
import com.mpas.mvp.merchant1.view.SaleDetailRecyclerViewAdapter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class SalesFragment extends Fragment {

    private static final String TAG = SalesFragment.class.getSimpleName();
    private List<SalesDetailModel.SalesDetailDB> data;
    private  SalesViewModel salesViewModel;
    private MerchantViewModel merchantViewModel;
    private SalesFragmentBinding binding;

    public SalesFragment(){
    }

    public static SalesFragment newInstance() {
        return new SalesFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.sales_fragment, container, false);
        //salesViewModel = ManamodiActivity.getSalesViewModel();
        //merchantViewModel = ManamodiActivity.getMerchantViewModel();
        merchantViewModel = ManagementActivity.getMerchantViewModel();
        salesViewModel = ManagementActivity.getSalesViewModel();

        binding.salesDetailRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));

        salesViewModel.getSaleDetailDbMutableLiveData().observe(requireActivity(), db->{
            binding.salesDetailRecyclerview.setAdapter( new SaleDetailRecyclerViewAdapter(db));
        });

        salesViewModel.getSalesSumPriceMutableLiveData().observe(requireActivity(), price ->{
            binding.salesSumPrice.setText(toPrice(price));
        });

        salesViewModel.getSalesDate().observe(requireActivity(), localDate -> {
            Log.e("searchDate",localDate.toString().replace("-",""));
            binding.searchDate.setText(String.format("%d년 %02d월 %02d일",localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth())+"  매입사별 정보 입니다");
        });

        //######################################
        CalRecyclerAdapter calRecyclerAdapter = new CalRecyclerAdapter(new CalRecyclerAdapter.CalendarListener() {
            @Override
            public void onRefresh(LocalDate localDate, int pos) {
                Log.e(TAG,"호출");
                salesViewModel.setSalesDate(localDate);
                salesViewModel.getSale_purchase(merchantViewModel.getMainMerchant().getValue().getBusinessNo(),
                        merchantViewModel.getMainMerchant().getValue().getMerchantNo(),localDate.toString().replace("-",""));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false);
        binding.calenderView.setLayoutManager(layoutManager);
        binding.calenderView.setAdapter(calRecyclerAdapter);
        binding.calenderView.scrollToPosition(calRecyclerAdapter.getItemCount()-1);
        //######################################

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();

        salesViewModel.getSale_purchase(merchantViewModel.getMainMerchant().getValue().getBusinessNo(),
                merchantViewModel.getMainMerchant().getValue().getMerchantNo(),
                TextConvert.localDateToString(Objects.requireNonNull(salesViewModel.getSalesDate().getValue())));
    }
}