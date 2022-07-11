package com.mpas.mvp.merchant1.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
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

import java.text.MessageFormat;
import java.text.NumberFormat;

public class SalesFragment extends Fragment {

    private static final String TAG = SalesFragment.class.getSimpleName();
    private static MerchantActivity merchantActivity;
    private SalesViewModel mViewModel;
    private SalesFragmentBinding binding;

    public static SalesFragment newInstance() {
        return new SalesFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.e(TAG,"onAttach");
        super.onAttach(context);

        if ( context instanceof Activity){
            merchantActivity = (MerchantActivity) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.sales_fragment,container,false);
        mViewModel = new ViewModelProvider(this).get(SalesViewModel.class);

        mViewModel.getSaleDbMutableLiveData().observe(requireActivity(), db->{
            binding.salesDetailRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.salesDetailRecyclerview.setAdapter( new SaleDetailRecyclerViewAdapter(db));
        });

        mViewModel.getSalesSumPriceMutableLiveData().observe(requireActivity(),price ->{
            Log.e(TAG, String.valueOf(price));
            binding.salesSumPrice.setText(MessageFormat.format("{0}원",toPrice(price)));
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getSale_purchase();
    }

    public String toPrice(Integer num) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return  numberFormat.format(Integer.valueOf(num));
    }
}